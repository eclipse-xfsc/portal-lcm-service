package gaiax.lcm.rest;

import gaiax.lcm.model.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConnectException.class})
    public ResponseEntity<ErrorDto> connectionRefused(HttpServletRequest httpServletRequest) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(httpServletRequest.getRequestURI(), "Connection error. Please, try later"));
    }

    @ExceptionHandler({WebClientResponseException.class})
    public ResponseEntity<ErrorDto> pageNotFound(HttpServletRequest httpServletRequest) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(httpServletRequest.getRequestURI(), "Page not found. Please, try later"));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDto> runtimeEx(HttpServletRequest httpServletRequest, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(httpServletRequest.getRequestURI(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(request.getContextPath(), "Unreachable URL"));
    }
}
