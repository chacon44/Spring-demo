package com.demo.controllers;


import com.demo.dto.ResponseDTO;
import com.demo.interfaces.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@org.springframework.web.bind.annotation.RestController
public class RestController {
//    @Autowired
//    private GreetingService greetingService;
//    @RequestMapping(value = "/demo", method = RequestMethod.GET, produces = {"application/json"})
//    public GreetingResponse greeting() {
//        return new GreetingResponse(greetingService.greeting());
//    }

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

    //this post mapping is used t specify that this method should handle post requests.
    @PostMapping(value = "/demo", produces = {"application/json"})
    ResponseDTO insert(@RequestBody AnswerService answerService)
    {
        return new ResponseDTO(answerService.getQuestion().question(), answerService.getAnswer());
    }


}
