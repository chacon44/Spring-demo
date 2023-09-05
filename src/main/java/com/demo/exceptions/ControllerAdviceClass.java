package com.demo.exceptions;

import com.demo.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice("com.demo.controllers")
public class ControllerAdviceClass extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OutOfIdException.class)
    public ResponseEntity<Object> handleMyException(OutOfIdException ex, WebRequest request) throws Exception {
        if (ex.getCode() == ErrorCode.OUT_OF_IDS) {

            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("OUT_OF_IDS", "Unable to generate new ID, maximum value: 10 was reached");

            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        } else if (ex.getCode() == ErrorCode.WRONG_HTTP_METHOD) {

            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("WRONG_HTTP_METHOD", "Unable to process request");

            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        } else return super.handleException(ex, request);
    }

}

