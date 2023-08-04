package com.easy2remember.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDoNotHaveAnyPostsException extends EmptyResultDataAccessException {
    public UserDoNotHaveAnyPostsException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
