package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class PostAnalyticsInfoWasNotFoundException extends EmptyResultDataAccessException {
    public PostAnalyticsInfoWasNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
