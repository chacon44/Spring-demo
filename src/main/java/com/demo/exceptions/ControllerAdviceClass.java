package com.demo.exceptions;

import com.demo.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class ControllerAdviceClass extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ErrorCode.WRONG_HTTP_METHOD, "Unable to process request");

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(CustomizedException.class)
    public ResponseEntity<Object> handleMyException(CustomizedException ex, WebRequest request) throws Exception {
        if (ex.getCode() == ErrorCode.OUT_OF_IDS) {

            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ErrorCode.OUT_OF_IDS, "Unable to generate new ID, maximum value was reached");

            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        } else return super.handleException(ex, request);
    }



}

