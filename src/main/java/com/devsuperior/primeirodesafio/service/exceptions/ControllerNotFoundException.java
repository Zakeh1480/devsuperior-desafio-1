package com.devsuperior.primeirodesafio.service.exceptions;

public class ControllerNotFoundException extends RuntimeException{

    public ControllerNotFoundException(String message) {
        super(message);
    }
}
