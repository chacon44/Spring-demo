package com.demo.controllers;

import com.demo.dto.ErrorDTO;
import com.demo.dto.RequestAnswerDTO;
import com.demo.dto.RequestDTO;
import com.demo.dto.ResponseDTO;
import com.demo.interfaces.AnswerService;
import com.demo.interfaces.IdManagement;
import com.demo.model.AnsweredQuestion;
import com.demo.service.GreetingService;
import com.demo.service.QuestionManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RestControllerTest {

    @Mock
    private GreetingService greetingService;
    @Mock
    private QuestionManagementService questionManagementService;
    @Mock
    private AnswerService answerService;
    @Mock
    private IdManagement idManagement;

    @InjectMocks
    private RestController restController;

    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.restController).build();
    }

    @Test
    public void testGreeting() throws Exception {
        mockMvc.perform(get("/greeting")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testPostQuestion_questionNotFound_CreatedReturned() throws Exception {

        long increment = 1L;
        // Arrange
        RequestDTO requestDTO = new RequestDTO("test question");
        ResponseDTO responseDTO = new ResponseDTO(1L, "test question", true);

        when(questionManagementService.returnMatchedQuestion(eq("test question"))).thenReturn(Optional.empty());
        when(answerService.getAnswer()).thenReturn(true);
        when(idManagement.incrementId()).thenReturn(increment);
        doNothing().when(questionManagementService).saveQuestion(any(ResponseDTO.class));

        // Act
        mockMvc.perform(post("/demo")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                //Assert
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(responseDTO.id()), Long.class))
                .andExpect(jsonPath("$.question", is(responseDTO.question())))
                .andExpect(jsonPath("$.answer", is(responseDTO.answer())))
                .andExpect(status().isCreated());

    }

    @Test
    public void testPostQuestion_questionFound_FoundReturned()throws Exception {

        // Arrange
        RequestDTO requestDTO = new RequestDTO("test question");
        ResponseDTO responseDTO = new ResponseDTO(1L, "test question", true);

        when(questionManagementService.returnMatchedQuestion(anyString())).thenReturn(Optional.of(responseDTO));

        // Act
        mockMvc.perform(post("/demo")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                //Assert
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(responseDTO.id()), Long.class))
                .andExpect(jsonPath("$.question", is(responseDTO.question())))
                .andExpect(jsonPath("$.answer", is(responseDTO.answer())))
                .andExpect(status().isFound());
    }

    @Test
    public void testPostQuestion_questionNull_BadRequestReturned() throws Exception {

        // Arrange
        RequestDTO requestDTO = new RequestDTO(null);
        ErrorDTO errorDTO = new ErrorDTO("Your question is missing or empty");

        mockMvc.perform(post("/demo")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                //Assert
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ErrorDescription", is(errorDTO.ErrorDescription())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostQuestion_questionEmpty_BadRequestReturned() throws Exception  {

        // Arrange
        RequestDTO requestDTO = new RequestDTO("");
        ErrorDTO errorDTO = new ErrorDTO("Your question is missing or empty");

        mockMvc.perform(post("/demo")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                //Assert
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ErrorDescription", is(errorDTO.ErrorDescription())))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testGetAnswer_questionFound_QuestionReturned() throws Exception{

        // Arrange
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("test", true);

        long id = 1L;
        when(questionManagementService.getQuestion(id)).thenReturn(Optional.of(answeredQuestion));

        mockMvc.perform(get("/demo/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(id)))
                //Assert
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.question", is(answeredQuestion.question())))
                .andExpect(jsonPath("$.answer", is(answeredQuestion.answer())))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetAnswer_questionEmpty_NullReturned() throws Exception{

        long id = 1L;
        when(questionManagementService.getQuestion(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/demo/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(id)))
                //Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAnswer_idEmptyOrNotFound_NotFoundReturned() throws Exception {

        long id = 1L;
        mockMvc.perform(delete("/demo/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(id)))
                //Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAnswer_idFound_ResponseReturned() throws Exception{

        // Arrange
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("test", true);
        ResponseDTO responseDTO = new ResponseDTO(1L, answeredQuestion.question(), answeredQuestion.answer());
        long id = 1L;
        when(questionManagementService.deleteQuestion(id)).thenReturn(Optional.of(answeredQuestion));

        mockMvc.perform(delete("/demo/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(id)))
                //Assert
                .andExpect(jsonPath("$.id", is(responseDTO.id()), Long.class))
                .andExpect(jsonPath("$.question", is(responseDTO.question())))
                .andExpect(jsonPath("$.answer", is(responseDTO.answer())))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutAnswer_questionNotFound_NullReturned() throws Exception{

        long id = 1L;
        RequestAnswerDTO requestAnswerDTO = new RequestAnswerDTO(true);

        when(questionManagementService.getQuestion(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/demo/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAnswerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPutAnswer_questionFound_QuestionReturned() throws Exception{

        long id = 1L;
        RequestAnswerDTO requestAnswerDTO = new RequestAnswerDTO(true);
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("test", true);
        ResponseDTO responseDTO = new ResponseDTO(1L, answeredQuestion.question(), answeredQuestion.answer());

        when(questionManagementService.getQuestion(id)).thenReturn(Optional.of(answeredQuestion));
        when(questionManagementService.returnQuestion(id)).thenReturn(responseDTO);

        mockMvc.perform(put("/demo/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAnswerDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(responseDTO.id()), Long.class))
                .andExpect(jsonPath("$.question", is(responseDTO.question())))
                .andExpect(jsonPath("$.answer", is(responseDTO.answer())))
                .andExpect(status().isOk());
    }
}

