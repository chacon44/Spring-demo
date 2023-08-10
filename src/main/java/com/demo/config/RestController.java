package com.demo.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})

    private String greeting() throws JsonProcessingException {
        //ReturnMessage bean = new ReturnMessage("greeting", "Hello, world");

        //return new ObjectMapper().writeValueAsString(bean);

        return "greeting: Hello, world";
    }
}
