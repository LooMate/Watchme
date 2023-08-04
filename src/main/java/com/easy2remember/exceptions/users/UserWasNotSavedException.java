package com.easy2remember.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserWasNotSavedException extends EmptyResultDataAccessException {
    public UserWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
