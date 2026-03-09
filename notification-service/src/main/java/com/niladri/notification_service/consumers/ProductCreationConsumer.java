package com.niladri.notification_service.consumers;

import com.niladri.common.constants.Topics;
import com.niladri.common.events.ProductCreationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCreationConsumer {

    @KafkaListener(topics = Topics.PRODUCT_CREATED)
    public void consumeProductCreationEvent(ProductCreationEvent productCreatedEvent) {
        log.info("Received product creation event: {}", productCreatedEvent.getDescription());
        // Here you can add logic to process the event, e.g., save it to a database or trigger a notification
    }
}
