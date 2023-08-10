package com.demo.config;

import com.fasterxml.jackson.annotation.JsonGetter;

public class ReturnMessage {

    public String action;
    private final String message;

    public ReturnMessage(String act, String mess){
        this.action = act;
        this.message = mess;
    }

    @JsonGetter("message")
    public String greeting() {
        return message;
    }
}