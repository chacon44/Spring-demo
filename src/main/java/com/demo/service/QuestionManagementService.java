package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import com.demo.repository.JdbcQuestionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class QuestionManagementService{

    @Autowired
    JdbcQuestionsRepository jdbcQuestionsRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuestionManagementService.class);

    public void saveQuestion(AnsweredQuestion answeredQuestion) {

        jdbcQuestionsRepository.save(answeredQuestion.question(),answeredQuestion.answer());
    }

    public void putAnswerIntoQuestion(ResponseDTO responseDTO) {

        jdbcQuestionsRepository.updateAnswer(responseDTO.id(), responseDTO.answer());
    }

    public ResponseDTO returnQuestion(long id) {

        return jdbcQuestionsRepository.returnQuestion(id);
    }

    public ResponseDTO returnIdByQuestion(String question){
        return jdbcQuestionsRepository.returnIdByQuestion(question);
    }

    public Optional<AnsweredQuestion> getQuestion(long id) {

        return jdbcQuestionsRepository.findById(id);
    }

    public Optional<AnsweredQuestion> deleteQuestion(long id) {

        jdbcQuestionsRepository.deleteById(id);
        return jdbcQuestionsRepository.findById(id);
    }

    public Optional<ResponseDTO> returnMatchedQuestion(String question) {

        return jdbcQuestionsRepository.findByQuestion(question);
    }
}
