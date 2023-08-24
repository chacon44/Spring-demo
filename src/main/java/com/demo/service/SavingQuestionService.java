package com.demo.service;

import com.demo.model.AnsweredQuestion;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SavingQuestionService {

    Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();

    public void saveQuestions(long id, String question, boolean answer) {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(question, answer);
        questionsMap.put(id, answeredQuestion);
    }

    public Optional<AnsweredQuestion> getQuestion(long id) {
        return Optional.ofNullable(questionsMap.get(id));
    }

    public Optional<AnsweredQuestion> deleteQuestion(long id) {
        return Optional.ofNullable(questionsMap.remove(id));
    }

    public String getStringQuestion(long id){
        return questionsMap.get(id).question();
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
}
