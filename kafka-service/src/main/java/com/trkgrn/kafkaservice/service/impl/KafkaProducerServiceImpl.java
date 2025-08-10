package com.trkgrn.kafkaservice.service.impl;

import com.trkgrn.common.kafka.EventConverterRegistry;
import com.trkgrn.common.kafka.KafkaMessage;
import com.trkgrn.kafkaservice.service.KafkaProducerService;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EventConverterRegistry converterRegistry;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, EventConverterRegistry converterRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.converterRegistry = converterRegistry;
    }

    @Override
    public void sendMessage(String topic, KafkaMessage message) {
        try {
            LOG.debug("Converting message with schema: {}", message.getSchemaName());

            SpecificRecord avroRecord = converterRegistry.convertToAvro(message);

            LOG.debug("Sending Avro record to Kafka topic: {}", topic);

            kafkaTemplate.send(topic, avroRecord)
                    .whenComplete((result, failure) -> {
                        if (failure != null) {
                            LOG.error("Failed to send message to topic: {}", topic, failure);
                        } else {
                            LOG.info("Message sent successfully to topic: {}, partition: {}, offset: {}",
                                    topic, result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });

        } catch (Exception e) {
            LOG.error("Error processing message for topic: {}", topic, e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
