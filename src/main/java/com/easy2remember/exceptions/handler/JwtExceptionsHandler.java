package com.easy2remember.exceptions.handler;


import com.easy2remember.exceptions.jwt.BadJwtTokenException;
import com.easy2remember.exceptions.jwt.JwtTokenWasExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class JwtExceptionsHandler {



    @ExceptionHandler(JwtTokenWasExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleJwtTokenWasExpiredException() {
        return "Jwt Token was expired";
    }

    @ExceptionHandler(BadJwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadJwtTokenException() {
        return "Bad jwt token";
    }

}
