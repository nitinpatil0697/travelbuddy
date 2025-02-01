package com.travelbuddy.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Create a WebClient Bean for Dependency Injection
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}