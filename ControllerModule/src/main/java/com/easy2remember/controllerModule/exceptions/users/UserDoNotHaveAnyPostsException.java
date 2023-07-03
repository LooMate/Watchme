package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDoNotHaveAnyPostsException extends EmptyResultDataAccessException {
    public UserDoNotHaveAnyPostsException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
