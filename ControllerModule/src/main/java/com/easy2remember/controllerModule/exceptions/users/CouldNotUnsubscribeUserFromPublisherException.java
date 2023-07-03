package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.DataAccessException;

public class CouldNotUnsubscribeUserFromPublisherException extends DataAccessException {
    public CouldNotUnsubscribeUserFromPublisherException(String msg) {
        super(msg);
    }
}
