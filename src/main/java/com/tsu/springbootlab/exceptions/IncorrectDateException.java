package com.tsu.springbootlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectDateException extends RuntimeException {
    public IncorrectDateException(String fieldName) {
        super("Incorrect date in " + fieldName);
    }
}
