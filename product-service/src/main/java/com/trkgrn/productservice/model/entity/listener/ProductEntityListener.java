package com.trkgrn.productservice.model.entity.listener;

import com.trkgrn.common.avro.ProductDeletedEvent;
import com.trkgrn.common.utils.EnvironmentUtils;
import com.trkgrn.common.utils.EventBuilder;
import com.trkgrn.common.utils.KafkaEventPublisher;
import com.trkgrn.common.utils.SpringContext;
import com.trkgrn.productservice.model.entity.Product;
import jakarta.persistence.PostRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductEntityListener {

    private static final Logger LOG = LoggerFactory.getLogger(ProductEntityListener.class);

    @PostRemove
    public void afterProductRemoved(Product product) {
        if (product == null || product.getId() == null) {
            LOG.warn("Product is null or has no ID, skipping event publishing.");
            return;
        }

        KafkaEventPublisher eventPublisher = SpringContext.getBean(KafkaEventPublisher.class);
        EventBuilder eventBuilder = SpringContext.getBean(EventBuilder.class);
        String applicationName = EnvironmentUtils.getProperty("spring.application.name");

        LOG.info("Product removed: id={}", product.getId());
        final ProductDeletedEvent event = ProductDeletedEvent.newBuilder()
                .setMetadata(eventBuilder.buildEventMetadata(applicationName))
                .setProductId(product.getId())
                .build();

        eventPublisher.publishEvent("product.deleted", event);
    }

}