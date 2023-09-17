package com.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RandomAnswerServiceTest {

    @Mock
    Random randomMock;

    @InjectMocks
    RandomAnswerService randomAnswerService;


    //I have to check the random generation of answers, not only variable type
    //I could get 1000 answers and check if there is a margin of 2% between the number of trues and falses

    @Test
    void getAnswer() {

        Mockito.when(randomMock.nextBoolean()).thenReturn(true);
        assertTrue(randomAnswerService.getAnswer());

        verify(randomMock).nextBoolean();
    }

    @Test
    void getQuestion() {
        assertNull(randomAnswerService.getQuestion());
    }
}