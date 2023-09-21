package com.demo.exceptions;

import com.demo.dto.ErrorResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ControllerAdviceClassTest {

    @Mock
    private WebRequest webRequestMock;

    @Mock
    private CustomizedException customizedExceptionMock;
    @Mock
    private HttpRequestMethodNotSupportedException exMock;
    @Mock
    private HttpHeaders headersMock;
    @Mock
    private HttpStatus statusMock;

    private final
    ControllerAdviceClass controllerAdviceClass = new ControllerAdviceClass();
    @Test
    void handleHttpRequestMethodNotSupported() {

        ResponseEntity<Object> responseEntity = controllerAdviceClass.handleHttpRequestMethodNotSupported(exMock,headersMock,statusMock,webRequestMock);
        assertNotNull(responseEntity.getBody());
        assertEquals(ErrorCode.WRONG_HTTP_METHOD, ((ErrorResponseDTO) responseEntity.getBody()).error());
    }

    @Test
    public void HandleMyException() throws Exception {

        //each time I request the code of custom exception, it will send "out of id"
        when(customizedExceptionMock.getCode()).thenReturn(ErrorCode.OUT_OF_IDS);

        ResponseEntity<Object> response = controllerAdviceClass.handleMyException(customizedExceptionMock, webRequestMock);

        //get error response DTO from response body
        ErrorResponseDTO errorResponseDTO = (ErrorResponseDTO) response.getBody();

        //check if not null
        assertNotNull(errorResponseDTO);
        assertEquals(ErrorCode.OUT_OF_IDS, errorResponseDTO.error());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals("Unable to generate new ID, maximum value was reached", errorResponseDTO.description());
    }
}