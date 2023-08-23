package com.demo.config;

import com.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("demo project")
public class Listeners {
    @Autowired
    private GreetingService greetingService;

    @EventListener(ContextRefreshedEvent.class)
    public void eventListener() {
        System.out.println(greetingService.greeting());
    }

}
