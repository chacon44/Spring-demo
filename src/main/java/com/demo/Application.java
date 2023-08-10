package com.demo;

import com.demo.config.ReturnMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {
    public static void main(String[] args) throws JsonProcessingException {
        ReturnMessage bean = new ReturnMessage("Hello, world");
        System.out.println(new ObjectMapper().writeValueAsString(bean));
    }
}