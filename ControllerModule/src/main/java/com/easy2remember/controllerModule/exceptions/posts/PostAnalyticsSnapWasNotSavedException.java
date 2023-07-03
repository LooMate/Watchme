package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostAnalyticsSnapWasNotSavedException extends EmptyResultDataAccessException {
    public PostAnalyticsSnapWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
