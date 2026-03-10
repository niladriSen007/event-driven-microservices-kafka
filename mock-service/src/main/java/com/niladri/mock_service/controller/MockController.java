package com.niladri.mock_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mock")
public class MockController {

    @GetMapping("/success")
    public ResponseEntity<String> getSuccess() {
        return ResponseEntity.ok("Success Response");
    }

    @GetMapping("/error")
    public ResponseEntity<Void> getError() {
        return ResponseEntity.internalServerError().build();
    }
}
