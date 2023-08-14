package com.demo.service;

import com.demo.interfaces.AnswerService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomAnswerService implements AnswerService {

    @Override
    public boolean getAnswer() {
        Random random = new Random();
        return random.nextBoolean();
    }
}