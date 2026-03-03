package com.niladri.product_service.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.niladri.product_service.events.ProductCreationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCreationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishProductCreationEvent(String topicName, ProductCreationEvent event) {
        kafkaTemplate.send(topicName, event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Failed to send product creation event to topic: {}", topicName, exception);
                    } else {
                        log.info("Product creation event sent successfully to topic: {}", topicName);
                    }
                });
    }
}
