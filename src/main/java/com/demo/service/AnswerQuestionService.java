package com.demo.service;


import com.demo.dto.RequestDTO;
import com.demo.interfaces.AnswerService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
@Service("answerQuestions")
public class AnswerQuestionService implements AnswerService {

    Random random = new Random();
    RequestDTO question = new RequestDTO("Would i complete this course?");

    AtomicLong id = new AtomicLong(0);
    long ID = 0;
    @Override
    public long getNewId() {
        //return id.getAndIncrement();
        return ID++;
    }

    @Override
    public boolean getAnswer() {
        return random.nextBoolean();
    }

    @Override
    public RequestDTO getQuestion() {
        return question;
    }

}
