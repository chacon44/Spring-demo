package com.demo.service;

import com.demo.interfaces.AnswerService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomAnswerService implements AnswerService {
    Random random = new Random();

    @Override
    public boolean getAnswer() {
        return random.nextBoolean();
    }
}