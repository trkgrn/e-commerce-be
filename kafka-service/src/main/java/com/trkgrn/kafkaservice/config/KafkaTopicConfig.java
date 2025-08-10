package com.trkgrn.kafkaservice.config;

import com.trkgrn.kafkaservice.properties.KafkaTopicProperties;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final GenericApplicationContext context;

    public KafkaTopicConfig(KafkaTopicProperties kafkaTopicProperties, GenericApplicationContext context) {
        this.kafkaTopicProperties = kafkaTopicProperties;
        this.context = context;
    }

    @PostConstruct
    public void registerTopics() {
        kafkaTopicProperties.getTopics().forEach(t -> {
            NewTopic topic = TopicBuilder.name(t.getName())
                    .partitions(t.getPartitions())
                    .replicas(t.getReplicas())
                    .build();

            context.registerBean(t.getName() + "Topic", NewTopic.class, () -> topic);
        });
    }
}