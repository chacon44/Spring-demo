package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import com.demo.repository.JdbcQuestionsRepository;
import com.demo.repository.Questions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionManagementService{

    @Autowired
    JdbcQuestionsRepository jdbcQuestionsRepository;
    private static final Logger log = LoggerFactory.getLogger(QuestionManagementService.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Map<Long, AnsweredQuestion> questionsMap = new HashMap<>();
    public void saveQuestion(ResponseDTO responseDTO) {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());

        Questions questions = new Questions(responseDTO.question(),responseDTO.answer());

        jdbcQuestionsRepository.save(questions);
        questionsMap.put(responseDTO.id(), answeredQuestion);
    }

    public void putQuestion(ResponseDTO responseDTO) {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(responseDTO.question(), responseDTO.answer());

        Questions questions = new Questions(responseDTO.question(),responseDTO.answer());

        jdbcQuestionsRepository.update(questions);
        questionsMap.put(responseDTO.id(), answeredQuestion);
    }

    public ResponseDTO returnQuestion(long id) {

        jdbcQuestionsRepository.findById(id);
        return new ResponseDTO(id, questionsMap.get(id).question(), questionsMap.get(id).answer());
    }

    public Optional<AnsweredQuestion> getQuestion(long id) {

        jdbcQuestionsRepository.getQuestionById(id);
        return Optional.ofNullable(questionsMap.get(id));
    }

    public Optional<AnsweredQuestion> deleteQuestion(long id) {

        jdbcQuestionsRepository.deleteById(id);
        return Optional.ofNullable(questionsMap.remove(id));
    }

    public Optional<ResponseDTO> returnMatchedQuestion(String question) {

        return questionsMap.entrySet()
                .stream()
                .filter(
                        entry -> entry.getValue().question().equals(question))
                .findFirst()
                .map(entry -> new ResponseDTO(entry.getKey(), entry.getValue().question(), entry.getValue().answer()));
    }
}
