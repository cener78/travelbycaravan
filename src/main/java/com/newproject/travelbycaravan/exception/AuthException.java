package com.newproject.travelbycaravan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
// exceptions are response

public class AuthException extends RuntimeException{

    public AuthException(String message) {
        super(message);
    }
}
