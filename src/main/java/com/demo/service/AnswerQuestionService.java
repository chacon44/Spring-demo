package com.demo.service;


import com.demo.dto.RequestDTO;
import com.demo.interfaces.AnswerService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service ("answerQuestions")
public class AnswerQuestionService implements AnswerService {

    Random random = new Random();
    RequestDTO question = new RequestDTO("Would i complete this course?");

    //String question = new RequestDTO("Would i complete this course?").toString();
    @Override
    public boolean getAnswer() {
        return random.nextBoolean();
    }

    @Override
    public RequestDTO getQuestion() {
        return question;
    }

}
