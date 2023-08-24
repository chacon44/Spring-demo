package com.demo.controllers;

import com.demo.dto.GreetingResponse;
import com.demo.dto.RequestAnswerDTO;
import com.demo.dto.RequestDTO;
import com.demo.dto.ResponseDTO;
import com.demo.interfaces.AnswerService;
import com.demo.model.AnsweredQuestions;
import com.demo.service.GreetingService;
import com.demo.service.SavingQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private GreetingService greetingService;
    @Autowired
    private SavingQuestions savingQuestions;
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
    @Autowired
    @Qualifier("answerQuestions")
    private AnswerService answerService;
    private AnsweredQuestions answeredQuestions;

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
        //Create new service that returns entire DTO for this

        if (!savingQuestions.checkQuestion(requestDTO.question())) {
            long temp_id = answerService.getNewId();
            boolean temp_answer = answerService.getAnswer();
            savingQuestions.saveQuestions(temp_id, requestDTO.question(), temp_answer);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(temp_id, requestDTO.question(), temp_answer), HttpStatus.CREATED );
        }
        else {
            return ResponseEntity.status(HttpStatus.FOUND).body(null);
        }
    }

    @GetMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> getAnswer(@PathVariable long id) {
        Optional<AnsweredQuestions> result = savingQuestions.getQuestion(id);
        //Did it because of IDEA suggestion
        return result
                .map(questions -> ResponseEntity.ok(new ResponseDTO(id, questions.question(), questions.answer())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> deleteAnswer(@PathVariable long id) {
        Optional<AnsweredQuestions> result = savingQuestions.deleteQuestion(id);
        return result
                .map(questions -> ResponseEntity.ok(new ResponseDTO(id, questions.question(), questions.answer())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> putAnswer(@PathVariable long id, @RequestBody RequestAnswerDTO requestAnswerDTO) {

        //Check if there is a question with such id
        if(savingQuestions.getQuestion(id) == null ||savingQuestions.getQuestion(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if(savingQuestions.checkQuestionAndId(id, savingQuestions.getStringQuestion(id))){
            //if there exist such question with such id
            savingQuestions.saveQuestions(id, savingQuestions.getStringQuestion(id), requestAnswerDTO.answer());
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(id, savingQuestions.getStringQuestion(id), requestAnswerDTO.answer()),HttpStatus.OK);

        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
