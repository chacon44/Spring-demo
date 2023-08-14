package com.demo.controllers;


import com.demo.dto.RandomAnswer;
import com.demo.service.RandomAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    private RandomAnswerService answerSupplierService;
    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})
    @Bean
    public RandomAnswer randomAnswer(){
        return new RandomAnswer(answerSupplierService.getAnswer());
    }

}
