package com.trkgrn.kafkaservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaTopicProperties {

    private List<TopicConfig> topics;

    public List<TopicConfig> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicConfig> topics) {
        this.topics = topics;
    }

    public static class TopicConfig {
        private String name;
        private int partitions;
        private int replicas;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getPartitions() {
            return partitions;
        }
        public void setPartitions(int partitions) {
            this.partitions = partitions;
        }
        public int getReplicas() {
            return replicas;
        }
        public void setReplicas(int replicas) {
            this.replicas = replicas;
        }
    }
}