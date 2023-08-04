package com.easy2remember.exceptions.handler;


import com.easy2remember.exceptions.roles.UserRoleWasNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class UserRoleExceptionHandler {


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserRoleWasNotSavedException.class)
    public String handleUserRoleWasNotSavedException(){
        return "Oops... Internal error";
    }

}
