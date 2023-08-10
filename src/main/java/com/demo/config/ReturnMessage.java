package com.demo.config;


import com.fasterxml.jackson.annotation.JsonGetter;

public class ReturnMessage {

    private final String message;

    public ReturnMessage(String mess){
        this.message = mess;
    }

    @JsonGetter("greeting")
    public String message() {
        return message;
    }
}