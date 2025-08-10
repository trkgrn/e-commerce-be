package com.trkgrn.common.utils;

import com.trkgrn.common.clients.KafkaServiceClient;
import com.trkgrn.common.kafka.KafkaMessage;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaServiceClient kafkaServiceClient;

    public KafkaEventPublisher(KafkaServiceClient kafkaServiceClient) {
        this.kafkaServiceClient = kafkaServiceClient;
    }

    public void publishEvent(String topic, SpecificRecord avroEvent) {
        try {
            LOG.debug("Publishing event to topic: {}, schema: {}", topic, avroEvent.getSchema().getName());

            KafkaMessage message = KafkaMessage.from(avroEvent);
            kafkaServiceClient.publish(topic, message);

            LOG.info("Event published successfully to topic: {}", topic);

        } catch (Exception e) {
            LOG.error("Failed to publish event to topic: {}", topic, e);
            throw new RuntimeException("Event publishing failed", e);
        }
    }

    public boolean publishEventSafely(String topic, SpecificRecord avroEvent) {
        try {
            publishEvent(topic, avroEvent);
            return true;
        } catch (Exception e) {
            LOG.error("Failed to publish event safely to topic: {}", topic, e);
            return false;
        }
    }
}