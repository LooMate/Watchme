package com.easy2remember.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostWasNotFoundException extends EmptyResultDataAccessException {
    public PostWasNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
