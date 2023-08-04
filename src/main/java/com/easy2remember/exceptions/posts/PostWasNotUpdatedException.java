package com.easy2remember.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostWasNotUpdatedException extends EmptyResultDataAccessException {
    public PostWasNotUpdatedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
