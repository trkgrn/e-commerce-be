package com.trkgrn.common.clients;

import com.trkgrn.common.kafka.KafkaMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kafka-service", path = "/v1/kafka")
public interface KafkaServiceClient {
    @PostMapping("/publish")
    ResponseEntity<Void> publish(@RequestParam String topic, @RequestBody KafkaMessage message);
}