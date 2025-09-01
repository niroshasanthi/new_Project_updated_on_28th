package com.example.demo.config;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SwaggerAggregatorController {

    private final DiscoveryClient discoveryClient;

    public SwaggerAggregatorController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/swagger-resources")
    public Mono<List<String>> swaggerResources() {
        // List of all services registered in Eureka
        List<String> services = discoveryClient.getServices();
        return Mono.just(
            services.stream()
                    .map(service -> "/" + service + "/v3/api-docs")
                    .collect(Collectors.toList())
        );
    }
}
