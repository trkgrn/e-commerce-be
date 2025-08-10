package com.trkgrn.kafkaservice.consumer;

import com.trkgrn.common.avro.MediaContainerDeletedEvent;
import com.trkgrn.common.clients.ProductGalleryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MediaContainerDeletedConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(MediaContainerDeletedConsumer.class);

    private final ProductGalleryClient productGalleryClient;

    public MediaContainerDeletedConsumer(ProductGalleryClient productGalleryClient) {
        this.productGalleryClient = productGalleryClient;
    }

    @KafkaListener(topics = "media.container.deleted", groupId = "kafka-service-group")
    public void consumeProductDeleted(MediaContainerDeletedEvent event) {
        LOG.info("Consumed MediaContainerDeletedEvent => MediaContainer ID: {}, Metadata: {}",
                event.getMediaContainerId(),
                event.getMetadata());

        try {
            productGalleryClient.deleteByMediaContainerId(event.getMediaContainerId());
            LOG.info("Successfully deleted Media Container galleries for Media Container ID: {}", event.getMediaContainerId());
        } catch (Exception e) {
            LOG.error("Failed to delete Media Container galleries for Media Container ID: {}. Error: {}", event.getMediaContainerId(), e.getMessage());
        }
    }

}