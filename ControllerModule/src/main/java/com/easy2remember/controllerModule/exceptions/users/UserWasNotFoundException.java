package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserWasNotFoundException extends EmptyResultDataAccessException {

    public UserWasNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
