package com.demo.dto;

public class GreetingResponse {
    private final String greeting;
    public GreetingResponse(String mess){
        this.greeting = mess;
    }
    public String getGreeting() {
        return greeting;
    }

}