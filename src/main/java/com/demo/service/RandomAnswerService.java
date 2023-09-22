package com.demo.service;

import com.demo.dto.RequestDTO;
import com.demo.interfaces.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

//Write ("test") in order to identify this service in controllers using qualifiers and referring to "test"
@Service("test")
public class RandomAnswerService implements AnswerService {

    @Autowired
    private Random returnRandom;

    @Override
    public boolean getAnswer() {
        return returnRandom.nextBoolean();
    }

    @Override
    public RequestDTO getQuestion() {
        return null;
    }

}