package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.DataAccessException;

public class CouldNotSavePublisherIdToSubscriberException extends DataAccessException {
    public CouldNotSavePublisherIdToSubscriberException(String msg) {
        super(msg);
    }
}
