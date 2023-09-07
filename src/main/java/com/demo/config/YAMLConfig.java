package com.demo.config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class YAMLConfig {

    private String name;
    private String environment;
    private boolean enabled;
    private final List<String> servers = new ArrayList<>();

    // standard getters and setters

}