package com.easy2remember.exceptions.users;

public class SpecifiedPostDoesNotBelongToUserException extends RuntimeException {
    public SpecifiedPostDoesNotBelongToUserException(String message) {
        super(message);
    }
}
