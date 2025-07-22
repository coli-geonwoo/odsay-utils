package com.devoops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SpringConfig {

    @Bean
    public RestClient.Builder builder() {
        return RestClient.builder();
    }
}
