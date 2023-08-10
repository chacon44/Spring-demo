package com.demo.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})

    private ReturnMessage greeting() throws JsonProcessingException {
        return new ReturnMessage("Hello, world");
    }
}
