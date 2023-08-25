package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionManagementService {

    Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();

    public void saveQuestion(ResponseDTO responseDTO) {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());
        questionsMap.put(responseDTO.id(), answeredQuestion);
    }
    public ResponseDTO returnQuestion(long id){
        return new ResponseDTO(id, questionsMap.get(id).question(),questionsMap.get(id).answer());
    }

    public Optional<AnsweredQuestion> getQuestion(long id) {
        return Optional.ofNullable(questionsMap.get(id));
    }

    public Optional<AnsweredQuestion> deleteQuestion(long id) {
        return Optional.ofNullable(questionsMap.remove(id));
    }

    public boolean checkQuestion(String question) {

        return questionsMap
                .entrySet() //Set of all key-value pairs in map
                .stream() // convert it to functional stream
                .anyMatch(entry -> entry.getValue().question().equals(question));
        //There "entry" is key-value pair. "entry.getValue()" is your value of type AnsweredQuestions. "entry.getValue().question()" is question, which we compare with the question from the method argument.
        //.anyMatch(...) return true, if condition inside it is true for at least a single element in the stream (so since we create stream from map, it would be true, if map includes an element, for which condition is true).
        //With this approach it would work even if later we would change answer type to be, say, also String.


    }
    public ResponseDTO returnMatchedQuestion(String question){
        final long[] matchedId = {-1L}; // Initialize to a value that won't be used in the map
        final boolean[] matchedAnswer = {true}; // Initialize to null

        questionsMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().question().equals(question))
                .findFirst()
                .ifPresent(entry -> {
                    matchedId[0] = entry.getKey();
                    matchedAnswer[0] = entry.getValue().answer();
                });
        return new ResponseDTO(matchedId[0], question, matchedAnswer[0]);
    }
}
