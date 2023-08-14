package com.demo.controllers;


import com.demo.dto.GreetingResponse;
import com.demo.dto.RandomAnswer;
import com.demo.interfaces.Answer;
import com.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@org.springframework.web.bind.annotation.RestController
public class RestController {
//    @Autowired
//    private GreetingService greetingService;
//    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})
//    public GreetingResponse greeting() {
//        return new GreetingResponse(greetingService.getGreeting());
//    }

    @Autowired
    private Answer answer;
    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})
    public RandomAnswer answer() {

        return answer.getAnswer();
    }

}
