package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionManagementServiceTest {

    ResponseDTO responseDTO = new ResponseDTO(1L, "Test question", true);
    AnsweredQuestion answeredQuestion = new AnsweredQuestion("Test question", true);
    private QuestionManagementService questionManagementService;

    @BeforeEach
    public void setUp() {
        questionManagementService = new QuestionManagementService();
    }

    @Test
    public void save_And_Get_Saved() {
        //ACT
        questionManagementService.saveQuestion(answeredQuestion);

        //ASSERT

        assertFalse(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());
        assertEquals(responseDTO.answer(), questionManagementService.getQuestion(responseDTO.id()).get().answer());

    }

    @Test
    public void search_For_Non_Saved_Question() {

        assertTrue(questionManagementService.returnMatchedQuestion(responseDTO.question()).isEmpty());
    }

    @Test
    public void get_Non_Saved() {

        assertTrue(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
    }

    @Test
    public void save_Question_Multiple_Times() {

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);

        assertFalse(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());

        assertEquals(responseDTO.answer(), questionManagementService.getQuestion(responseDTO.id()).get().answer());

        ResponseDTO responseDTO_2 = new ResponseDTO(responseDTO.id() + 1, responseDTO.question(), responseDTO.answer());
        AnsweredQuestion answeredQuestion1 = new AnsweredQuestion("Test 2", false);
        questionManagementService.saveQuestion(answeredQuestion1);

        //ASSERT

        questionManagementService.saveQuestion(answeredQuestion);
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());
        assertEquals(responseDTO_2.question(), questionManagementService.getQuestion(responseDTO_2.id()).get().question());

    }

    @Test
    public void save_And_Search_For_Question() {

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);

        //ASSERT
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());
        assertFalse(questionManagementService.returnMatchedQuestion(responseDTO.question()).isEmpty());
    }

    @Test
    public void remove_Non_Saved() {
        assertTrue(questionManagementService.deleteQuestion(responseDTO.id()).isEmpty());
    }

    @Test
    public void save_Get_Remove_and_Get_Again() {

        //ACT
        questionManagementService.saveQuestion(answeredQuestion);

        assertFalse(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());
        assertEquals(responseDTO.answer(), questionManagementService.getQuestion(responseDTO.id()).get().answer());

        questionManagementService.deleteQuestion(responseDTO.id());
        assertTrue(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
    }
}
