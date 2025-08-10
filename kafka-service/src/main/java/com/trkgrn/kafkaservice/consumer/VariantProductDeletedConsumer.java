package com.trkgrn.kafkaservice.consumer;

import com.trkgrn.common.avro.VariantProductDeletedEvent;
import com.trkgrn.common.clients.ProductGalleryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VariantProductDeletedConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(VariantProductDeletedConsumer.class);

    private final ProductGalleryClient productGalleryClient;

    public VariantProductDeletedConsumer(ProductGalleryClient productGalleryClient) {
        this.productGalleryClient = productGalleryClient;
    }

    @KafkaListener(topics = "variant.product.deleted", groupId = "kafka-service-group")
    public void consumeProductDeleted(VariantProductDeletedEvent event) {
        LOG.info("Consumed VariantProductDeletedEvent => Product ID: {}, Metadata: {}",
                event.getProductId(),
                event.getMetadata());

        try {
            productGalleryClient.deleteByVariantProductId(event.getProductId());
            LOG.info("Successfully deleted product galleries for Variant Product ID: {}", event.getProductId());
        } catch (Exception e) {
            LOG.error("Failed to delete product galleries for Variant Product ID: {}. Error: {}", event.getProductId(), e.getMessage());
        }
    }

}