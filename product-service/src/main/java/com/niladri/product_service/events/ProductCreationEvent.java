package com.niladri.product_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreationEvent {
    private final String productId;
    private final String name;
    private final String description;
    private final double price;
}
