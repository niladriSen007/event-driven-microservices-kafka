package com.niladri.notification_service.consumers;

import com.niladri.common.constants.Topics;
import com.niladri.common.events.ProductCreationEvent;
import com.niladri.notification_service.error.NonRetryable;
import com.niladri.notification_service.error.Retryable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCreationConsumer {

    private final RestTemplate restTemplate;

    @KafkaListener(topics = Topics.PRODUCT_CREATED)
    public void consumeProductCreationEvent(ProductCreationEvent productCreatedEvent) {
        // in Non Retryable exception, the event will not be retried and will be discarded and the event will be stored into dead letter topic if configured
//        throw new NonRetryable("Simulated non-retryable exception for product creation event with ID: " + productCreatedEvent.getProductId());


        log.info("Received product creation event: {}", productCreatedEvent.getDescription());
        // Here you can add logic to process the event, e.g., save it to a database or trigger a notification

        try {
            ResponseEntity<String> res = restTemplate.exchange("http://localhost:8082/api/mock/error", HttpMethod.GET, null, String.class);
            if (res.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully consumed product creation event");
            }
        } catch (ResourceAccessException ex) { // mwans the resource is not running or there is a network issue, so we can retry
            log.error("Failed to consume product creation event, : {}", ex.getMessage());
            throw new Retryable("Simulated retryable exception for product creation event with ID: " + productCreatedEvent.getProductId());
        } catch (HttpServerErrorException ex) {
            log.error("Failed to consume product creation event, message - {}", ex.getMessage());
            throw new NonRetryable("Simulated non-retryable exception for product creation event with ID: " + productCreatedEvent.getProductId());
        } catch (Exception ex) {
            log.error("Failed to consume product creation event, cause{}", ex.getMessage());
            throw new NonRetryable("Simulated non-retryable exception for product creation event with ID: " + productCreatedEvent.getProductId());
        }
    }
}
