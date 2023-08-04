package com.easy2remember.exceptions.jwt;

public class BadJwtTokenException extends RuntimeException {
    public BadJwtTokenException(String message) {
        super(message);
    }
}
