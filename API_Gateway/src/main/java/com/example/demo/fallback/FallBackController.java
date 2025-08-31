package com.example.demo.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "APIGateway", description = "API Gateway routing and aggregation endpoints")
@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping
    public ResponseEntity<String> fallback() {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("Service is currently unavailable. Please try again later.");
    }
}
