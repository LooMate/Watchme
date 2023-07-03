package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserWasNotDeletedException extends EmptyResultDataAccessException {
    public UserWasNotDeletedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
