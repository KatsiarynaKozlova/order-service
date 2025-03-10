package com.modsen.software.order.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public static final String KAFKA_TOPIC = "order-status-changed-topic";

    public void sendOrderStatusNotification(OrderStatusDetails details) {
        Message<OrderStatusDetails> message = MessageBuilder
                .withPayload(details)
                .setHeader(KafkaHeaders.TOPIC, KAFKA_TOPIC)
                .build();
        kafkaTemplate.send(message);
    }
}
