package com.trkgrn.kafkaservice.service;

import com.trkgrn.common.kafka.KafkaMessage;

public interface KafkaProducerService {
    void sendMessage(String topic, KafkaMessage message);
}
