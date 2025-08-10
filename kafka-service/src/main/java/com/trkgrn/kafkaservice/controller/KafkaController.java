package com.trkgrn.kafkaservice.controller;

import com.trkgrn.common.kafka.KafkaMessage;
import com.trkgrn.kafkaservice.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/kafka")
public class KafkaController {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaController.class);


    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@RequestParam String topic, @RequestBody KafkaMessage message) {
        try {
            LOG.info("Publishing message to topic: {}, schema: {}", topic, message.getSchemaName());
            producerService.sendMessage(topic, message);
            LOG.info("Message published successfully to topic: {}", topic);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error("Failed to publish message to topic: {}", topic, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
