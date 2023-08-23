package com.demo.interfaces;

import com.demo.dto.RequestDTO;

public interface AnswerService {

    long getNewId();
    boolean getAnswer();
    RequestDTO getQuestion();
}
