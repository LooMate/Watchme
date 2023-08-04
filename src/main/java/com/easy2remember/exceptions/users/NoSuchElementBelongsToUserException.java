package com.easy2remember.exceptions.users;

import org.springframework.dao.DataAccessException;

public class NoSuchElementBelongsToUserException extends DataAccessException {
    public NoSuchElementBelongsToUserException(String message) {
        super(message);
    }
}
