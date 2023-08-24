package com.demo.service;

import com.demo.model.AnsweredQuestions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SavingQuestions {

    Map<Long, AnsweredQuestions> questionsMap = new HashMap<>();

    public void saveQuestions(long id, String question, boolean answer) {
        AnsweredQuestions answeredQuestions = new AnsweredQuestions(question, answer);
        questionsMap.put(id, answeredQuestions);
    }

    public Optional<AnsweredQuestions> getQuestion(long id) {
        return Optional.ofNullable(questionsMap.get(id));
    }

    public Optional<AnsweredQuestions> deleteQuestion(long id) {
        return Optional.ofNullable(questionsMap.remove(id));
    }

    public String getStringQuestion(long id){
        return questionsMap.get(id).question();
    }
    public boolean checkQuestion(String question) {

        return questionsMap.containsValue(new AnsweredQuestions(question, true))
                || questionsMap.containsValue(new AnsweredQuestions(question, false));
    }

    public boolean checkQuestionAndId(long id, String question){
        return !questionsMap.get(id).question().isEmpty();
    }

}
