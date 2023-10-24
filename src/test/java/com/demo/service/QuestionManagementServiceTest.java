package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import com.demo.repository.QuestionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QuestionManagementServiceTest {

    @Mock
    QuestionsRepository questionsRepository;

    ResponseDTO responseDTO = new ResponseDTO(1L, "Test question", true);

    AnsweredQuestion answeredQuestion = new AnsweredQuestion("Test question", true);

    private QuestionManagementService questionManagementService;

    @BeforeEach
    public void setUp() {

        questionManagementService = new QuestionManagementService(questionsRepository);
    }

    @Test
    public void save_And_Get_Saved() {

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);
        questionManagementService.getQuestion(1L);

        //ASSERT
        Mockito.verify(questionsRepository, Mockito.times(1))
                .save(
                        Mockito.eq(responseDTO.question()),
                        Mockito.eq(responseDTO.answer()));

        Mockito.verify(questionsRepository, Mockito.times(1))
                .findById(
                        Mockito.eq(1L));
    }

    @Test
    public void search_For_Non_Saved_Question() {

        questionManagementService.returnMatchedQuestion(responseDTO.question());

        Mockito.verify(questionsRepository, Mockito.times(1))
                .findByQuestion(Mockito.eq(responseDTO.question()));
    }

    @Test
    public void get_Non_Saved() {
        questionManagementService.getQuestion(70000L);

        Mockito.verify(questionsRepository, Mockito.times(1))
                .findById(Mockito.eq(70000L));
    }

    @Test
    public void save_Question_Multiple_Times() {

        ResponseDTO responseDTO3 = new ResponseDTO(30L, "Test question", true);

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);
        Mockito.verify(questionsRepository, Mockito.times(1))
                .save(Mockito.eq(responseDTO3.question()),
                        Mockito.eq(responseDTO3.answer()));




    }

    @Test
    public void save_And_Search_For_Question() {

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);
        questionManagementService.returnMatchedQuestion(answeredQuestion.question());

        //ASSERT
        Mockito.verify(questionsRepository, Mockito.times(1))
                .findByQuestion(Mockito.eq(answeredQuestion.question()));
    }

    @Test
    public void remove_Non_Saved() {
        assertTrue(questionManagementService.deleteQuestion(responseDTO.id()).isEmpty());
    }

    @Test
    public void save_Get_Remove_and_Get_Again() {

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);
        Mockito.verify(questionsRepository, Mockito.times(1))
                .save(
                        Mockito.eq(responseDTO.question()),
                        Mockito.eq(responseDTO.answer()));



        questionManagementService.deleteQuestion(responseDTO.id());
        assertTrue(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
    }
}
