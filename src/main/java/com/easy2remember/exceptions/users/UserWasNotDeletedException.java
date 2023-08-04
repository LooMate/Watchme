package com.easy2remember.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserWasNotDeletedException extends EmptyResultDataAccessException {
    public UserWasNotDeletedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
