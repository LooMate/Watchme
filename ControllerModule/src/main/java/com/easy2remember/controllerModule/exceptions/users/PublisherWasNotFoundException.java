package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class PublisherWasNotFoundException extends EmptyResultDataAccessException {

    public PublisherWasNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
