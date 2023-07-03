package com.easy2remember.controllerModule.exceptions.posts;

public class PostNotificationCouldNotBeSentException extends RuntimeException{
    public PostNotificationCouldNotBeSentException(String message) {
        super(message);
    }
}
