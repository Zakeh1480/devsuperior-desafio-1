package com.devsuperior.primeirodesafio.controller.exceptions;

import com.devsuperior.primeirodesafio.service.exceptions.ControllerNotFoundException;
import com.devsuperior.primeirodesafio.service.exceptions.DatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> notFound(ControllerNotFoundException controllerNotFoundException,
                                                  HttpServletRequest httpServletRequest) {

        StandardError standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("Client not found");
        standardError.setMessage(controllerNotFoundException.getMessage());
        standardError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(404).body(standardError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException databaseException,
                                                           HttpServletRequest httpServletRequest) {

        StandardError standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.BAD_REQUEST.value());
        standardError.setError("Database exception");
        standardError.setMessage(databaseException.getMessage());
        standardError.setPath(httpServletRequest.getRequestURI());

        return ResponseEntity.status(400).body(standardError);
    }
}
