package com.easy2remember.exceptions.jwt;

public class JwtTokenWasExpiredException extends RuntimeException{
    public JwtTokenWasExpiredException(String message) {
        super(message);
    }
}
