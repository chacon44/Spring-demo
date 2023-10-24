package com.demo.repository;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;

import java.util.Optional;

public interface QuestionsRepository{

    boolean save(String question, boolean answer);

    boolean updateAnswer(long Id, boolean answer);

    boolean deleteById(Long id);
    Optional <ResponseDTO> findByQuestion(String question);
    Optional<AnsweredQuestion> findById(Long id);
    Optional <ResponseDTO> returnQuestion (Long id);

    Optional <ResponseDTO> returnIdByQuestion(String question);
}
