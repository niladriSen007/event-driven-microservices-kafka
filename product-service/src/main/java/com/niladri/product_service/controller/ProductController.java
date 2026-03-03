package com.niladri.product_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niladri.product_service.dto.ProductDto;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {

    }
}
