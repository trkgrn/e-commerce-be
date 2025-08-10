package com.trkgrn.mediaservice.model.listener;

import com.trkgrn.common.avro.MediaContainerDeletedEvent;
import com.trkgrn.common.utils.EnvironmentUtils;
import com.trkgrn.common.utils.EventBuilder;
import com.trkgrn.common.utils.KafkaEventPublisher;
import com.trkgrn.common.utils.SpringContext;
import com.trkgrn.mediaservice.model.MediaContainer;
import jakarta.persistence.PostRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MediaContainerEntityListener {

    private static final Logger LOG = LoggerFactory.getLogger(MediaContainerEntityListener.class);

    @PostRemove
    public void afterMediaContainerRemoved(MediaContainer mediaContainer) {
        if (mediaContainer == null || mediaContainer.getId() == null) {
            LOG.warn("Media Container is null or has no ID, skipping event publishing.");
            return;
        }

        KafkaEventPublisher eventPublisher = SpringContext.getBean(KafkaEventPublisher.class);
        EventBuilder eventBuilder = SpringContext.getBean(EventBuilder.class);
        String applicationName = EnvironmentUtils.getProperty("spring.application.name");

        LOG.info("Media Container removed: id={}", mediaContainer.getId());
        final MediaContainerDeletedEvent event = MediaContainerDeletedEvent.newBuilder()
                .setMetadata(eventBuilder.buildEventMetadata(applicationName))
                .setMediaContainerId(mediaContainer.getId())
                .build();

        eventPublisher.publishEvent("media.container.deleted", event);
    }

}