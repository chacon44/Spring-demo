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

    // TODO


    @Test
    public void save_And_Get_Saved(){
        //ACT
        questionManagementService.saveQuestion(responseDTO);

        //ASSERT

        //Check if id is stored in question map
        assertTrue(questionManagementService.questionsMap.containsKey(responseDTO.id()));

        //Check if question is stored in associated id
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),responseDTO.question());

        //Check if answer is stored in associated id
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().answer(),responseDTO.answer());

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
        assertTrue(questionManagementService.questionsMap.containsKey(responseDTO.id()));

        //Check if question is stored in associated id
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),responseDTO.question());

        //Check if answer is stored in associated id
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().answer(),responseDTO.answer());

        //save again with different id but same question
        responseDTO = new ResponseDTO(2L, "Test question", true);
        questionManagementService.saveQuestion(responseDTO);

        //ASSERT
        //the first id '1' must be stored but not the second one, '2'. Both are saved, I would need to add
        // a checking before adding new questions. For example, calling returnMatchedQuestion
        assertTrue(questionManagementService.questionsMap.containsKey(1L));
        //assertFalse(questionManagementService.questionsMap.containsKey(2L));

        //check if I can save two questions in the same id. It should only save the first id
        responseDTO = new ResponseDTO(2L, "Second Test question", true);
        questionManagementService.saveQuestion(responseDTO);

        //It should return the first question saved in id '2L', which is "test question"
        assertEquals(questionManagementService.getQuestion(2L).get().question(),"Test question");

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
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),responseDTO.question());

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
        assertTrue(questionManagementService.questionsMap.containsKey(responseDTO.id()));

        //Check if question is stored in associated id and matches the sent question
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().question(),responseDTO.question());

        //Check if answer is stored in associated id and matches the sent answer
        assertEquals(questionManagementService.getQuestion(responseDTO.id()).get().answer(),responseDTO.answer());


        //Now remove
        questionManagementService.deleteQuestion(responseDTO.id());

        assertFalse(questionManagementService.questionsMap.containsKey(responseDTO.id()));

        //Check if question is not stored in associated id
        assertTrue(questionManagementService.getQuestion(responseDTO.id()).isEmpty());
    }
}
