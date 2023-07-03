package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserWasNotUpdatedException extends EmptyResultDataAccessException {

    public UserWasNotUpdatedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
