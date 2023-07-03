package com.easy2remember.controllerModule.exceptions.posts;

public class NoAccessToPostDetailsException extends RuntimeException{
    public NoAccessToPostDetailsException(String message) {
        super(message);
    }
}
