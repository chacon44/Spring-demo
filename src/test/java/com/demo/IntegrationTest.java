package com.demo;

import com.demo.config.H2DataSource;
import com.demo.repository.JdbcQuestionsRepository;
import com.demo.repository.QuestionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        QuestionsRepository.class,
        JdbcQuestionsRepository.class,
        H2DataSource.class
})
public class IntegrationTest {

    @Autowired
    QuestionsRepository questionsRepository;

    @Autowired
    DataSource dataSource;

    @Test
    void whenInjectInMemoryDataSource_thenReturnCorrectEmployeeCount() {

        assertEquals("question1", questionsRepository.returnQuestion(1L).question());
    }

}
