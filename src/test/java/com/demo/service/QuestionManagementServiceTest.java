package com.demo.service;

import com.demo.dto.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionManagementServiceTest {

    ResponseDTO responseDTO = new ResponseDTO(1L, "Test question", true);

    private QuestionManagementService questionManagementService;
    @BeforeEach
    public void setUp() {
        questionManagementService = new QuestionManagementService();
    }

    @Test
    public void save_And_Get_Saved(){
        //ACT
        questionManagementService.saveQuestion(responseDTO);

        //ASSERT

        //Check if id is stored in question map
        assertFalse(questionManagementService.getQuestion(responseDTO.id()).isEmpty());

        //Check if question is stored in associated id
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());

        //Check if answer is stored in associated id
        assertEquals(responseDTO.answer(), questionManagementService.getQuestion(responseDTO.id()).get().answer());

    }
    @Test
    public void search_For_Non_Saved_Question(){
        //I send a question and search if it exists and its saved in any id, if not it will return Optional.empty
        //So i can assert if it is empty

        assertTrue(questionManagementService.returnMatchedQuestion(responseDTO.question()).isEmpty());
    }
    @Test
    public void get_Non_Saved(){
        //when I get a non saved question
        //since getQuestion return Optional.ofNullable(questionsMap.get(id))
        //In case there is no question in that id, it will be Optional.empty
        //So I check assert if this is Optional.empty

        assertTrue(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
    }
    @Test
    public void save_Question_Multiple_Times(){

        //ACT
        questionManagementService.saveQuestion(responseDTO);

        //Check if id is stored in question map
        assertFalse(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
        //Check if question is stored in associated id
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());

        //Check if answer is stored in associated id
        assertEquals(responseDTO.answer(), questionManagementService.getQuestion(responseDTO.id()).get().answer());

        //save again with different id but same question
        ResponseDTO responseDTO_2 = new ResponseDTO(responseDTO.id() +1, responseDTO.question(), responseDTO.answer());
        questionManagementService.saveQuestion(responseDTO_2);

        //ASSERT

        questionManagementService.saveQuestion(responseDTO);
        assertEquals(responseDTO.question(),questionManagementService.getQuestion(responseDTO.id()).get().question());

        //It should return the same question in other id
        assertEquals(responseDTO_2.question(),questionManagementService.getQuestion(responseDTO_2.id()).get().question());

    }
    @Test
    public void save_And_Search_For_Question(){

        //ACT

        //I firstly save the question and answer
        questionManagementService.saveQuestion(responseDTO);

        //save it in the questions map with its respective id
        //questionsMap.put(responseDTO.id(), answeredQuestion);

        //ASSERT
        //Get saved question in a specific id and check if it matches the question saved in the responseDTO
        // that we provided before
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());

        //Search for question, it shouldn't return Optional.empty
        assertFalse(questionManagementService.returnMatchedQuestion(responseDTO.question()).isEmpty());
    }

    @Test
    public void remove_Non_Saved(){
        //when I delete a non saved question
        //since deleteQuestion return Optional.ofNullable(questionsMap.remove(id))
        //In case there is no question in that id, it will be Optional.empty
        //So I check assert if this is Optional.empty


        assertTrue(questionManagementService.deleteQuestion(responseDTO.id()).isEmpty());
        }

    @Test
    public void save_Get_Remove_and_Get_Again(){

        //ACT
        questionManagementService.saveQuestion(responseDTO);
        //questionsMap.put(responseDTO.id(), answeredQuestion);

        //Check if id is stored in question map
        assertFalse(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
        //Check if question is stored in associated id and matches the sent question
        assertEquals(responseDTO.question(), questionManagementService.getQuestion(responseDTO.id()).get().question());

        //Check if answer is stored in associated id and matches the sent answer
        assertEquals(responseDTO.answer(), questionManagementService.getQuestion(responseDTO.id()).get().answer());

        //Now remove
        questionManagementService.deleteQuestion(responseDTO.id());

        //Check if question is not stored in associated id
        assertTrue(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
    }
}
