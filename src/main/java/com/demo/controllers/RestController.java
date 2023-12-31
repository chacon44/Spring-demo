package com.demo.controllers;

import com.demo.dto.*;
import com.demo.model.AnsweredQuestion;
import com.demo.service.GreetingService;
import com.demo.service.QuestionManagementService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private GreetingService greetingService;
    @Autowired
    private QuestionManagementService questionManagementService;

    @GetMapping(value = "/greeting", produces = {"application/json"})
    public GreetingResponse greeting() {
        return new GreetingResponse(greetingService.greeting());
    }

    @PostMapping(value = "/demo", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> postQuestion(@RequestBody RequestDTO requestDTO) {
        if (requestDTO.question() == null || requestDTO.question().isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO("Your question is missing or empty");

            logger.error("error because question is empty");
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }

        Optional<ResponseDTO> matchedQuestion = questionManagementService.returnMatchedQuestion(requestDTO.question());

        if (matchedQuestion.isPresent()) {
            logger.debug("question found");
            return ResponseEntity.status(HttpStatus.FOUND).body(matchedQuestion.get());
        } else {
            logger.debug("question not found");
            questionManagementService.saveQuestion(new AnsweredQuestion(requestDTO.question(), new Random().nextBoolean()));

            ResponseDTO responseDTO2 = questionManagementService.returnIdByQuestion(requestDTO.question());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO2);
        }
    }

    @GetMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> getAnswer(@PathVariable long id) {
        Optional<AnsweredQuestion> result = questionManagementService.getQuestion(id);

        return result.map(questions -> {
            logger.info("Successful");
            return ResponseEntity.ok(new ResponseDTO(id, questions.question(), questions.answer()));
        }).orElseGet(() -> {
            logger.error("This id doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        });
    }

    @DeleteMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> deleteAnswer(@PathVariable long id) {
        Optional<AnsweredQuestion> result = questionManagementService.deleteQuestion(id);
        return result.map(questions -> ResponseEntity.ok(new ResponseDTO(id, questions.question(), questions.answer())))
                .orElseGet(() -> {
                    logger.error("This id doesn't exist");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    @PutMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> putAnswer(@PathVariable long id, @RequestBody RequestAnswerDTO requestAnswerDTO) {

        if (questionManagementService.getQuestion(id).isEmpty()){
            logger.error("This id doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            ResponseDTO responseDTO = new ResponseDTO(id, questionManagementService.returnQuestion(id).question(), requestAnswerDTO.answer());
            questionManagementService.putAnswerIntoQuestion(responseDTO);
            logger.debug("question put correctly");
            return ResponseEntity.status(HttpStatus.OK).body(questionManagementService.returnQuestion(id));
        }
    }
}
