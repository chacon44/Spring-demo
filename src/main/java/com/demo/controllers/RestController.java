package com.demo.controllers;


import com.demo.dto.GreetingResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})

    public GreetingResponse greeting() {
        return new GreetingResponse("Hello, world");
    }

}
