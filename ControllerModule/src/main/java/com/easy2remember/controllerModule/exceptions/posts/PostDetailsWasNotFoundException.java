package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostDetailsWasNotFoundException extends EmptyResultDataAccessException {
    public PostDetailsWasNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
