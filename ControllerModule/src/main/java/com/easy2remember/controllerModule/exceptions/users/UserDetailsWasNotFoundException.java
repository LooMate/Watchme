package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDetailsWasNotFoundException extends EmptyResultDataAccessException {
    public UserDetailsWasNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
