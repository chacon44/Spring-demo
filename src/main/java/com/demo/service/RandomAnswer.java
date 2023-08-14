package com.demo.service;

import com.demo.interfaces.Answer;
import org.springframework.stereotype.Service;

@Service
public class RandomAnswer implements Answer {

    @Override
    public RandomAnswer getAnswer() {
        return getAnswer();
    }
}