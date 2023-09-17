package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QuestionManagementServiceTest {

    
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
        Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();


        //ACT
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionManagementService.saveQuestion(responseDTO);
        questionsMap.put(1L, answeredQuestion);
        String question1 = questionsMap.get(1L).question();

        //save again
        questionsMap.put(1L, answeredQuestion);
        String question2 = questionsMap.get(1L).question();

        String savedQuestion = questionManagementService.getQuestion(responseDTO.id()).toString();
        System.out.println(savedQuestion);
        System.out.println(question1);

        //ASSERT
        //Saved twice
        assertEquals(question1, question2);

    }
    @Test
    public void save_And_Search_For_Questions_and_Non_Saved_Questions(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);
        Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();
        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        System.out.println(questionManagementService.getQuestion(responseDTO.id()).get().question());
        //ASSERT
        //Get saved
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),question);

        //Search for question
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),question, questionManagementService.returnMatchedQuestion(responseDTO.question()).get().question());


        //Search for non saved question

        assertThrows(NoSuchElementException.class,
                ()->{
                    questionManagementService.returnMatchedQuestion("not existing question").get().question();
                });
        //assertEquals(Optional.empty(),questionManagementService.returnMatchedQuestion("not existing question"));


    }
    @Test
    public void testSaveQuestion() {

        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);
        Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();
        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),question);


    }

    @Test
    public void remove_Non_Saved(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);
        Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();


        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Remove non saved
        assertThrows(NoSuchElementException.class,
                ()->{
                    questionManagementService.deleteQuestion(2L).get().question();
                });    }
    @Test
    public void save_Get_Remove_and_Get_Again(){
        //ARRANGE
        long id = 1L;
        String question = "Test question";
        boolean answer = true;
        responseDTO = new ResponseDTO(id, question, answer);
        Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();

        //ACT
        questionManagementService.saveQuestion(responseDTO);
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),question);

        questionManagementService.deleteQuestion(responseDTO.id());
        //Remove

        assertThrows(NoSuchElementException.class,
                ()->{
            questionManagementService.deleteQuestion(responseDTO.id()).get().question();
                });
        //Get Again

        assertThrows(NoSuchElementException.class,
                ()->{
                    questionManagementService.getQuestion(responseDTO.id()).get().question();
                });

    }
}
