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

    private final ControllerAdviceClass controllerAdviceClass = new ControllerAdviceClass();

    @Test
    void handleHttpRequestMethodNotSupported() {

        ResponseEntity<Object> responseEntity = controllerAdviceClass.handleHttpRequestMethodNotSupported(exMock, headersMock, statusMock, webRequestMock);
        assertNotNull(responseEntity.getBody());
        assertEquals(ErrorCode.WRONG_HTTP_METHOD, ((ErrorResponseDTO) responseEntity.getBody()).error());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());

    }

    @Test
    public void HandleMyException() throws Exception {

        ResponseEntity<Object> response = controllerAdviceClass.handleMyException(customizedExceptionMock, webRequestMock);

        ErrorResponseDTO errorResponseDTO = (ErrorResponseDTO) response.getBody();

        assertNotNull(errorResponseDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals("Your question is missing or empty", errorResponseDTO.description());
    }
}
