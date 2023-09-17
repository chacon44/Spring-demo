package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QuestionManagementServiceTest {

    @Mock
    Map<Long, AnsweredQuestion> questionsMap;
    @Mock
    AnsweredQuestion answeredQuestion;

    @Mock
    ResponseDTO responseDTO;
    @InjectMocks
    private QuestionManagementService questionManagementService;


    // cover various scenarios of use:
    //TODO


    @Test
    public void save_question_multiple_times(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);

        //ACT
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionManagementService.saveQuestion(responseDTO);
        questionsMap.put(2L, answeredQuestion);

        String question1 = questionsMap.get(2L).question();

        //save again
        questionsMap.put(2L, answeredQuestion);

        String savedQuestion = questionManagementService.getQuestion(responseDTO.id()).toString();
        System.out.println(savedQuestion);
        System.out.println(question1);

        //ASSERT
        //Get saved first time
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).toString(), question);

    }
    @Test
    public void save_And_Search_For_Questions_and_Non_Saved_Questions(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);

        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved
        assertNotNull(questionManagementService.getQuestion(responseDTO.id()));

        //Search for question
        assertNotNull(questionManagementService.returnMatchedQuestion(responseDTO.question()));

        //Search for non saved question
        assertEquals(Optional.empty(),questionManagementService.returnMatchedQuestion("not existing question"));


    }
    @Test
    public void testSaveQuestion() {

        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);

        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved
        assertNotNull(questionManagementService.getQuestion(responseDTO.id()));

        //Get non saved
        //assertNull(questionManagementService.getQuestion(10L));
    }

    @Test
    public void remove_Non_Saved(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);

        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved
        assertNotNull(questionManagementService.getQuestion(responseDTO.id()));
        //Remove
        assertEquals(Optional.empty(),questionManagementService.deleteQuestion(10L));
    }
    @Test
    public void save_Get_Remove_and_Get_Again(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);

        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved
        assertNotNull(questionManagementService.getQuestion(responseDTO.id()));
        //Remove
        assertNotNull(questionManagementService.deleteQuestion(responseDTO.id()));
        //Get Again
        assertEquals(Optional.empty(),questionManagementService.getQuestion(10L));

    }
}
