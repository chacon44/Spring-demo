package com.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RandomAnswerServiceTest {

    @Mock
    private Random randomMock;

    @Spy
    @InjectMocks
    private RandomAnswerService randomAnswerService;

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