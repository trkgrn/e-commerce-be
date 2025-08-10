package com.trkgrn.productservice.model.entity.listener;

import com.trkgrn.common.avro.VariantProductDeletedEvent;
import com.trkgrn.common.utils.EnvironmentUtils;
import com.trkgrn.common.utils.EventBuilder;
import com.trkgrn.common.utils.KafkaEventPublisher;
import com.trkgrn.common.utils.SpringContext;
import com.trkgrn.productservice.model.entity.VariantProduct;
import jakarta.persistence.PostRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariantProductEntityListener {

    private static final Logger LOG = LoggerFactory.getLogger(VariantProductEntityListener.class);

    @PostRemove
    public void afterVariantProductRemoved(VariantProduct variantProduct) {
        if (variantProduct == null || variantProduct.getId() == null) {
            LOG.warn("Product is null or has no ID, skipping event publishing.");
            return;
        }

        KafkaEventPublisher eventPublisher = SpringContext.getBean(KafkaEventPublisher.class);
        EventBuilder eventBuilder = SpringContext.getBean(EventBuilder.class);
        String applicationName = EnvironmentUtils.getProperty("spring.application.name");

        LOG.info("Variant Product removed: id={}", variantProduct.getId());
        final VariantProductDeletedEvent event = VariantProductDeletedEvent.newBuilder()
                .setMetadata(eventBuilder.buildEventMetadata(applicationName))
                .setProductId(variantProduct.getId())
                .build();

        eventPublisher.publishEvent("variant.product.deleted", event);
    }

}