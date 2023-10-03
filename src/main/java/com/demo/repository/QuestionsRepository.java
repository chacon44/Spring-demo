package com.demo.repository;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository{

    int save(Questions questions);

    int update(Questions questions);

    int deleteById(Long id);
    List<Questions> findByQuestion(String question);
    Optional<Questions> findById(Long id);
    String getQuestionById(Long id);
}
