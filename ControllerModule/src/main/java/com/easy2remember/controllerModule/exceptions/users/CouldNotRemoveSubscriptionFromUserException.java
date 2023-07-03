package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.DataAccessException;

public class CouldNotRemoveSubscriptionFromUserException extends DataAccessException {
    public CouldNotRemoveSubscriptionFromUserException(String msg) {
        super(msg);
    }
}
