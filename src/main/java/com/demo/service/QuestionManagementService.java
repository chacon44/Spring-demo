package com.demo.service;

import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import com.demo.repository.QuestionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QuestionManagementService {

    private final QuestionsRepository questionsRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuestionManagementService.class);

    @Autowired
    public QuestionManagementService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public void saveQuestion(AnsweredQuestion answeredQuestion) {

        questionsRepository.save(answeredQuestion.question(), answeredQuestion.answer());
    }

    public void putAnswerIntoQuestion(ResponseDTO responseDTO) {

        questionsRepository.updateAnswer(responseDTO.id(), responseDTO.answer());
    }

    public Optional <ResponseDTO> returnQuestion(long id) {

        return questionsRepository.returnQuestion(id);
    }

    public Optional <ResponseDTO> returnIdByQuestion(String question) {
        return questionsRepository.returnIdByQuestion(question);
    }

    public Optional<AnsweredQuestion> getQuestion(long id) {

        return questionsRepository.findById(id);
    }

    public Optional<AnsweredQuestion> deleteQuestion(long id) {

        questionsRepository.deleteById(id);
        return questionsRepository.findById(id);
    }

    public Optional<ResponseDTO> returnMatchedQuestion(String question) {

        return questionsRepository.findByQuestion(question);
    }
}
