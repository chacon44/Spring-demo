package com.demo.controllers;

import com.demo.dto.GreetingResponse;
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
    ResponseEntity<ResponseDTO> insert(@RequestBody RequestDTO requestDTO) {
        if (requestDTO.question() == null || requestDTO.question().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //Create new service that returns entire DTO for this
        long temp_id = answerService.getNewId();
        boolean temp_answer = answerService.getAnswer();
        savingQuestions.saveQuestions(temp_id, requestDTO.question(), temp_answer);
        return ResponseEntity.ok(new ResponseDTO(temp_id, requestDTO.question(), temp_answer));
    }

    @GetMapping(value = "/demo/{id}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseDTO> insert(@PathVariable long id) {
        Optional<AnsweredQuestions> result = savingQuestions.getQuestion(id);
        //Did it because of IDEA suggestion
        return result
                .map(questions -> ResponseEntity.ok(new ResponseDTO(id, questions.question(), questions.answer())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
