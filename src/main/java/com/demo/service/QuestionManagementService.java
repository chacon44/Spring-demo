package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionManagementService {

    @Autowired
    AnsweredQuestion answeredQuestion;
    @Autowired
    ResponseDTO responseDTO;
    Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();

    public void saveQuestion(ResponseDTO responseDTO) {
        answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);
    }

    public ResponseDTO returnQuestion(long id) {
        return new ResponseDTO(id, questionsMap.get(id).question(), questionsMap.get(id).answer());
    }

    public Optional<AnsweredQuestion> getQuestion(long id) {
        return Optional.ofNullable(questionsMap.get(id));
    }

    public Optional<AnsweredQuestion> deleteQuestion(long id) {
        return Optional.ofNullable(questionsMap.remove(id));
    }

    public Optional <ResponseDTO> returnMatchedQuestion(String question) {
        return questionsMap.entrySet()
                .stream()
                .filter(
                        entry -> entry.getValue().question().equals(question))
                .findFirst()
                .map(entry -> new ResponseDTO(entry.getKey(), entry.getValue().question(),entry.getValue().answer()));
    }
}
