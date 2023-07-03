package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.DataAccessException;

public class NoSuchElementBelongsToUserException extends DataAccessException {
    public NoSuchElementBelongsToUserException(String message) {
        super(message);
    }
}
