package com.demo;

import com.demo.config.ApplicationConfig;
import com.demo.service.GreetingService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        GreetingService greetingService = context.getBean(GreetingService.class);
        greetingService.getGreeting("Hello, World!");

    }
}