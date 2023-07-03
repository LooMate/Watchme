package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostDetailsWasNotSavedException extends EmptyResultDataAccessException {

    public PostDetailsWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
