package com.demo.config;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class StartEvent {
    @Async
    @EventListener
    void sendMsgEvent(String message) {
        System.out.println("==EmailListener 2 ==="+message);
    }

}
