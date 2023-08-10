package com.demo.service;

public class GreetingService {

    private static String greeting;

    public GreetingService(String greeting) {
        super();
        GreetingService.greeting = greeting;
    }

    public static String getGreeting() {
        return greeting;
    }

}
