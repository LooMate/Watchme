package com.easy2remember.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDoesNotExistsException extends EmptyResultDataAccessException {

    public UserDoesNotExistsException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
