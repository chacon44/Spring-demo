package com.demo.config;

import com.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Random;

@ComponentScan(basePackages = "com.demo")
@Configuration
@EnableWebMvc
public class ApplicationConfig {
    @Value("Hello, world!")
    private String greeting;
    @Autowired
    private GreetingService greetingService;
    @Bean
    public GreetingService greetingService(){
        return new GreetingService(greeting);
    }
}
