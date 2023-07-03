package com.easy2remember.controllerModule.exceptions.users;

import org.springframework.dao.DataAccessException;

public class CouldNotSaveSubscriberToPublisherException extends DataAccessException {
    public CouldNotSaveSubscriberToPublisherException(String msg) {
        super(msg);
    }
}
