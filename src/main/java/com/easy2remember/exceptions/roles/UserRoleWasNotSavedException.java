package com.easy2remember.exceptions.roles;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserRoleWasNotSavedException extends EmptyResultDataAccessException {
    public UserRoleWasNotSavedException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
