package com.easy2remember.exceptions.users;

public class UsernameDoesNotMatchLoggedUserException extends RuntimeException{
    public UsernameDoesNotMatchLoggedUserException(String message) {
        super(message);
    }
}
