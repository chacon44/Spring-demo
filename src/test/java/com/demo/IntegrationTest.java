package com.demo;

import com.demo.config.H2DataSource;
import com.demo.controllers.RestController;
import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import com.demo.repository.JdbcQuestionsRepository;
import com.demo.repository.QuestionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "h2")
@ContextConfiguration(classes = {
        H2DataSource.class,
        JdbcQuestionsRepository.class
})
public class IntegrationTest {

    @Autowired
    QuestionsRepository questionsRepository;

    @Test
    void returnQuestion() {

        assertEquals("question2", questionsRepository.returnQuestion(2L).question());
        assertEquals(false, questionsRepository.returnQuestion(2L).answer());

    }

    @Test
    void updateAnswer() {

        questionsRepository.updateAnswer(4L, true);
        assertEquals(true, questionsRepository.returnQuestion(4L).answer());
    }

    @Test
    public void save(){

        assertEquals(1, questionsRepository.save("question1", true));
        assertEquals("question1", questionsRepository.returnQuestion(1L).question());
    }
    @Test
    public void deleteAnswer() {

        //If operation is performed, it returns 1
        assertEquals(1, questionsRepository.deleteById(4L));
    }
    @Test
    public void deleteAnswer_NotFound() {

        //If operation is not performed, it returns 0
        assertEquals(0, questionsRepository.deleteById(5L));
    }
    @Test
    public void findByQuestion(){

        assertEquals(Optional.of(new ResponseDTO(2,"question2", false)), questionsRepository.findByQuestion("question2"));
    }

    @Test
    public void findById(){

        assertEquals(Optional.of(new AnsweredQuestion("question2", false)), questionsRepository.findById(2L));
    }

    @Test
    public void returnIdByQuestion(){

        assertEquals((new ResponseDTO(2,"question2", false)), questionsRepository.returnIdByQuestion("question2"));
    }

}
