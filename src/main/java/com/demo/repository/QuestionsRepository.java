package com.demo.repository;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;

import java.util.Optional;

public interface QuestionsRepository{

    int save(String question, boolean answer);

    int updateAnswer(long Id, boolean answer);

    int deleteById(Long id);
    Optional <ResponseDTO> findByQuestion(String question);
    Optional<AnsweredQuestion> findById(Long id);
    ResponseDTO returnQuestion (Long id);

    ResponseDTO returnIdByQuestion(String question);
}
