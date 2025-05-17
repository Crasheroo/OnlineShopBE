package com.crashero.user.core;

import com.crashero.user.model.event.CartEvent;
import com.crashero.user.model.event.CheckoutEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.cart-events:cart-events}")
    private String cartTopic;

    @Value("${kafka.topics.checkout-events:checkout-events}")
    private String checkoutTopic;

    public KafkaSender(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCartEvent(CartEvent event) {
        kafkaTemplate.send(cartTopic, event);
        log.info("Cart event sent to Kafka: {}", event);
    }

    public void sendCheckoutEvent(CheckoutEvent event) {
        kafkaTemplate.send(checkoutTopic, event);
        log.info("Checkout event sent to Kafka: {}", event);
    }
}
