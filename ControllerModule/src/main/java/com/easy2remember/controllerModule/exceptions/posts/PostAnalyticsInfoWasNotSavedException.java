package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostAnalyticsInfoWasNotSavedException extends EmptyResultDataAccessException {
    public PostAnalyticsInfoWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
