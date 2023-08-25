package com.demo.controllers;

import com.demo.dto.GreetingResponse;
import com.demo.dto.RequestAnswerDTO;
import com.demo.dto.RequestDTO;
import com.demo.dto.ResponseDTO;
import com.demo.interfaces.AnswerService;
import com.demo.interfaces.IdManagement;
import com.demo.model.AnsweredQuestion;
import com.demo.service.GreetingService;
import com.demo.service.IdManagementService;
import com.demo.service.QuestionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    //Services
    @Autowired
    private GreetingService greetingService;
    @Autowired
    private QuestionManagementService questionManagementService;
    @Autowired
    private IdManagementService idManagementService;
    //return list os services that implements AnswerService interface
//    @Autowired
//    List<AnswerService> list;
    //@Autowired
    //@Qualifier("test")
    //private AnswerService answerService;
//    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})
//    public RandomAnswer randomAnswer() {
//        return new RandomAnswer(answerService.getAnswer());
//    }

    //Model classes

    //Interfaces
    @Autowired
    @Qualifier("answerQuestions")
    private AnswerService answerService;
    private AnsweredQuestion answeredQuestion;

    @Autowired
    @Qualifier("IdManagement")
    private IdManagement idManagement;

    @GetMapping(value = "/greeting", produces = {"application/json"})
    public GreetingResponse greeting() {
        return new GreetingResponse(greetingService.greeting());
    }

    //this post mapping is used t specify that this method should handle post requests.
    @PostMapping(value = "/demo", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> postQuestion(@RequestBody RequestDTO requestDTO) {
        if (requestDTO.question() == null || requestDTO.question().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (questionManagementService.checkQuestion(requestDTO.question())) {
            //if question already exists
            //get id, question and answer
            ResponseDTO responseDTO = questionManagementService.returnMatchedQuestion(requestDTO.question());
            return ResponseEntity.status(HttpStatus.FOUND).body(responseDTO);
        } else {
            //if question is new
            ResponseDTO responseDTO = new ResponseDTO(idManagement.incrementId(), requestDTO.question(), answerService.getAnswer());
            questionManagementService.saveQuestion(responseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
    }

    @GetMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> getAnswer(@PathVariable long id) {
        Optional<AnsweredQuestion> result = questionManagementService.getQuestion(id);
        //Did it because of IDEA suggestion
        return result.map(questions -> ResponseEntity.ok(new ResponseDTO(id, questions.question(), questions.answer()))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> deleteAnswer(@PathVariable long id) {
        Optional<AnsweredQuestion> result = questionManagementService.deleteQuestion(id);
        String regex = "--> THIS QUESTION HAS BEEN DELETED"; //to clarify action performed when the object is sent
        return result.map(questions -> ResponseEntity.ok(new ResponseDTO(id, questions.question().concat(regex), questions.answer()))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> putAnswer(@PathVariable long id, @RequestBody RequestAnswerDTO requestAnswerDTO) {

        if (questionManagementService.getQuestion(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else {
            ResponseDTO responseDTO = new ResponseDTO(id, questionManagementService.returnQuestion(id).question(), requestAnswerDTO.answer());
            questionManagementService.saveQuestion(responseDTO);
            return ResponseEntity.status(HttpStatus.OK).body(questionManagementService.returnQuestion(id));
        }
    }
}
