package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostAnalyticsInfoWasNotDeletedException extends EmptyResultDataAccessException {
    public PostAnalyticsInfoWasNotDeletedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
