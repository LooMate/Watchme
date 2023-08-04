package com.easy2remember.exceptions.users;

public class UserIdDoesNotMatchLoggedUserException extends RuntimeException{
    public UserIdDoesNotMatchLoggedUserException(String message) {
        super(message);
    }
}
