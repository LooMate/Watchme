package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.DataAccessException;

public class UserAlreadyExistsException extends DataAccessException {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
