package com.crashero.user.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topics.cart-events}")
    private String cartEventsTopic;

    @Value(value = "${kafka.topics.checkout-events}")
    private String checkoutEventsTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic cartEventsTopic() {
        return new NewTopic("cart-events", 1, (short) 1);
    }

    @Bean
    public NewTopic checkoutEventsTopic() {
        return new NewTopic("checkout-events", 1, (short) 1);
    }

    @Bean
    public NewTopic cartEventsDLT() {
        return TopicBuilder.name(cartEventsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic checkoutEventsDLT() {
        return TopicBuilder.name(checkoutEventsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
