package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserWasNotSavedException extends EmptyResultDataAccessException {
    public UserWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
