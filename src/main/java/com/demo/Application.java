package com.demo;

import com.demo.dto.GreetingResponse;
import com.demo.model.AnsweredQuestions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {
    private AnsweredQuestions answeredQuestions;

    public static void main(String[] args) throws JsonProcessingException {
        GreetingResponse bean = new GreetingResponse("Hello, world");
        System.out.println(new ObjectMapper().writeValueAsString(bean));
    }
}