package com.demo.controllers;

import com.demo.dto.RequestDTO;
import com.demo.dto.ResponseDTO;
import com.demo.interfaces.AnswerService;
import com.demo.interfaces.IdManagement;
import com.demo.service.GreetingService;
import com.demo.service.IdManagementService;
import com.demo.service.QuestionManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class restControllerTest {

    // Create a mock of your service
    @Mock
    private GreetingService greetingService;
    @Mock
    private QuestionManagementService questionManagementService;
    @Mock
    private IdManagementService idManagementService;
    @Mock
    @Qualifier("answerQuestions")
    private AnswerService answerService;
    @Mock
    @Qualifier("IdManagement")
    private IdManagement idManagement;

    @InjectMocks
    private RestController restController;

    private MockMvc mockMvc;

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

//    @Test
//    public void testPostQuestion() throws Exception {
//        mockMvc.perform(get("/demo")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testPostQuestion() {
        // Arrange
        RequestDTO requestDTO = new RequestDTO("test question");

        ResponseDTO responseDTO = new ResponseDTO(1L, "test question", true);

        RestController restController = new RestController();
        restController.setQuestionManagementService(new QuestionManagementService());
        restController.setIdManagementService(new IdManagementService(1L));

        when(questionManagementService.returnMatchedQuestion(anyString())).thenReturn(Optional.empty());
        when(answerService.getAnswer()).thenReturn(true);
        when(idManagement.incrementId()).thenReturn(1L);
        doNothing().when(questionManagementService).saveQuestion(any(ResponseDTO.class));

        // Act
        ResponseEntity<?> result = restController.postQuestion(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDTO, result.getBody());

        verify(questionManagementService, times(1)).returnMatchedQuestion(anyString());
        verify(answerService, times(1)).getAnswer();
        verify(idManagement, times(1)).incrementId();
        verify(questionManagementService, times(1)).saveQuestion(any(ResponseDTO.class));
    }
}

    // Add more tests for postQuestion, getAnswer, deleteAnswer, and putAnswer
