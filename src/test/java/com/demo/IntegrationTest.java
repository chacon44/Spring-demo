package com.demo;

import com.demo.config.H2DataSource;
import com.demo.controllers.RestController;
import com.demo.repository.JdbcQuestionsRepository;
import com.demo.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
        QuestionsRepository.class,
        JdbcQuestionsRepository.class,
        H2DataSource.class
})
public class IntegrationTest {

    @Autowired
    RestController restController;

    @Autowired
    QuestionsRepository questionsRepository;


}
