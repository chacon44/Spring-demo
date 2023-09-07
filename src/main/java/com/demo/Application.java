package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws JsonProcessingException {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");

        String variable = "Hello John";
        logger.debug("Printing variable value {} ", variable);
    }
}