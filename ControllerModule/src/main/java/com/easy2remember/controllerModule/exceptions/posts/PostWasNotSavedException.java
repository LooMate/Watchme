package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostWasNotSavedException extends EmptyResultDataAccessException {
    public PostWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
