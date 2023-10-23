package com.demo;

import com.demo.config.H2DataSource;
import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import com.demo.repository.JdbcQuestionsRepository;
import com.demo.repository.QuestionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals("question2", questionsRepository.returnQuestion(2L).get().question());
        assertEquals(false, questionsRepository.returnQuestion(2L).get().answer());
    }

    @Test
    void returnQuestion_NotFound() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            questionsRepository.returnQuestion(4000L).get().answer();
        });
    }

    @Test
    void updateAnswer() {

        assertTrue(questionsRepository.updateAnswer(4L, true));
    }

    @Test
    void updateAnswer_NotFound() {

        assertFalse(questionsRepository.updateAnswer(4000L, true));
    }



    @Test
    public void save(){

        assertTrue(questionsRepository.save("question1", true));
        assertEquals("question1", questionsRepository.returnQuestion(5L).get().question());
        assertEquals(true, questionsRepository.returnQuestion(5L).get().answer());
    }

    @Test
    public void NotSaved(){

        questionsRepository.save("question1", true);

        assertFalse(questionsRepository.save("question1", true));
    }
    @Test
    public void deleteAnswer() {

        assertTrue(questionsRepository.deleteById(1L));
    }
    @Test
    public void deleteAnswer_NotFound() {

        assertFalse(questionsRepository.deleteById(7000L));
    }
    @Test
    public void findByQuestion(){

        assertEquals(Optional.of(new ResponseDTO(2,"question2", false)), questionsRepository.findByQuestion("question2"));
    }

    @Test
    public void findByQuestion_NotFound(){

        assertEquals(Optional.empty(), questionsRepository.findByQuestion("NotFoundQuestion"));
    }

    @Test
    public void findById(){

        assertEquals(Optional.of(new AnsweredQuestion("question2", false)), questionsRepository.findById(2L));
    }

    @Test
    public void findById_NotFound(){

        assertEquals(Optional.empty(), questionsRepository.findById(20000L));
    }

    @Test
    public void returnIdByQuestion(){

        ResponseDTO responseDTO_temp = new ResponseDTO(2L, "question2", false);
        Optional<ResponseDTO> responseDTO = Optional.of(responseDTO_temp);

        assertEquals(responseDTO, questionsRepository.returnIdByQuestion("question2"));
    }

    @Test
    public void returnIdByQuestion_NotFound(){

        assertThrows(IndexOutOfBoundsException.class, () -> {
            questionsRepository.returnIdByQuestion("NotFoundQuestion");
        });
    }

}
