package com.carson.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication(scanBasePackages = "com.carson.order")
public class OrderApplication implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
