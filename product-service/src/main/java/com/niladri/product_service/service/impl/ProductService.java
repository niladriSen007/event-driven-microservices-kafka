package com.niladri.product_service.service.impl;

import org.springframework.stereotype.Service;

import com.niladri.product_service.dto.ProductDto;
import com.niladri.product_service.events.ProductCreationEvent;
import com.niladri.product_service.model.Product;
import com.niladri.product_service.producers.ProductCreationProducer;
import com.niladri.product_service.repository.ProductRepository;
import com.niladri.product_service.service.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductCreationProducer productCreationProducer;

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();
        productRepository.save(product);
        productCreationProducer.publishProductCreationEvent("product-created-event-topic",
                ProductCreationEvent.builder()
                        .productId(product.getId().toString())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build());

    }
}
