package com.niladri.product_service.service.impl;


import org.springframework.stereotype.Service;

import com.niladri.common.constants.Topics;
import com.niladri.product_service.dto.ProductDto;
import com.niladri.common.events.ProductCreationEvent;
import com.niladri.product_service.model.Product;
import com.niladri.product_service.producers.ProductCreationProducer;
import com.niladri.product_service.repository.ProductRepository;
import com.niladri.product_service.service.IProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductCreationProducer productCreationProducer;

    @Override
    @Transactional(value = "kafkaTransactionManager", rollbackFor = {Throwable.class, ConnectException.class}, noRollbackFor = {})
    public void createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();
        Product savedProduct = productRepository.save(product);
        productCreationProducer.publishProductCreationEvent(Topics.PRODUCT_CREATED,
                savedProduct.getId().toString(),
                ProductCreationEvent.builder()
                        .productId(savedProduct.getId().toString())
                        .name(savedProduct.getName())
                        .description(savedProduct.getDescription())
                        .price(product.getPrice())
                        .build());

    }
}
