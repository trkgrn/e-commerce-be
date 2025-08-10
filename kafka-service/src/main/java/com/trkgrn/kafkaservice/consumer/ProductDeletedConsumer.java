package com.trkgrn.kafkaservice.consumer;

import com.trkgrn.common.avro.ProductDeletedEvent;
import com.trkgrn.common.clients.ProductGalleryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductDeletedConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDeletedConsumer.class);

    private final ProductGalleryClient productGalleryClient;

    public ProductDeletedConsumer(ProductGalleryClient productGalleryClient) {
        this.productGalleryClient = productGalleryClient;
    }

    @KafkaListener(topics = "product.deleted", groupId = "kafka-service-group")
    public void consumeProductDeleted(ProductDeletedEvent event) {
        LOG.info("Consumed ProductDeletedEvent => Product ID: {}, Metadata: {}",
                event.getProductId(),
                event.getMetadata());

        try {
            productGalleryClient.deleteByBaseProductId(event.getProductId());
            LOG.info("Successfully deleted product galleries for Product ID: {}", event.getProductId());
        } catch (Exception e) {
            LOG.error("Failed to delete product galleries for Product ID: {}. Error: {}", event.getProductId(), e.getMessage());
        }
    }

}