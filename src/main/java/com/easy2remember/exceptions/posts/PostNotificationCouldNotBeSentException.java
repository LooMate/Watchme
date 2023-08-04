package com.easy2remember.exceptions.posts;

public class PostNotificationCouldNotBeSentException extends RuntimeException{
    public PostNotificationCouldNotBeSentException(String message) {
        super(message);
    }
}
