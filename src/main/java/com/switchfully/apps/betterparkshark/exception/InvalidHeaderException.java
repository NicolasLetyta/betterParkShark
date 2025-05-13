package com.switchfully.apps.betterparkshark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidHeaderException extends RuntimeException {
    public InvalidHeaderException(String message) {
        super(message);
    }
}

