package com.demo.config;

import com.demo.database.Database;
import com.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Random;

@ComponentScan(basePackages = "com.demo")
@Configuration
@EnableWebMvc
public class ApplicationConfig {
    @Value("Hello, world!")
    private String greeting;

    @Autowired
    DataSource dataSource;
    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
    @Autowired
    private GreetingService greetingService;

    @Bean
    public Random returnRandom(){
        return new Random();
    }
    @Bean
    public GreetingService greetingService(){
        return new GreetingService(greeting);
    }
}
