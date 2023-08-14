package com.demo.service;

import com.demo.interfaces.Answer;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RandomAnswer implements Answer {

    @Override
    public boolean getAnswer() {
        Random random = new Random();
        boolean answer = random.nextBoolean();
        return new RandomAnswer().getAnswer();
    }
}