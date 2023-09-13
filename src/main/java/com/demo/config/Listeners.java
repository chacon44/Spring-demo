package com.demo.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.demo.service.GreetingService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("demo project")
public class Listeners implements ApplicationContextAware {


    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Listeners.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Autowired
    private GreetingService greetingService;

    @EventListener(ContextRefreshedEvent.class)
    public void eventListener() {
        System.out.println(greetingService.greeting());

        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();

        if (activeProfiles.length == 0) {
            logger.info("No active Spring profiles.");
        } else {
            logger.info("Active Spring profiles: {}", String.join(", ", activeProfiles));
        }

        if (Arrays.asList(activeProfiles).contains("prod")) {
            Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.ERROR);
        }
        if (Arrays.asList(activeProfiles).contains("dev")) {
            Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.DEBUG);
        }
    }

}
