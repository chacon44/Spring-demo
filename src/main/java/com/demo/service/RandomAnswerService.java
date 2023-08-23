package com.demo.service;

import com.demo.dto.RequestDTO;
import com.demo.interfaces.AnswerService;
import org.springframework.stereotype.Service;

import java.util.Random;

//Write ("test") in order to identify this service in controllers using qualifiers and referring to "test"
@Service("test")
public class RandomAnswerService implements AnswerService {
    Random random = new Random();

    @Override
    public long getNewId() {
        return 0;
    }

    @Override
    public boolean getAnswer() {
        return random.nextBoolean();
    }

    @Override
    public RequestDTO getQuestion() {
        return null;
    }

}