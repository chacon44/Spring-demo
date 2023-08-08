package com.demo.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GreetingService implements ApplicationListener {

    private final String greeting;

    public GreetingService(String greeting){
        super();
        this.greeting = greeting;
    }

    public String getGreeting(String greeting){
        System.out.println(greeting);
        return greeting;
    }

}
