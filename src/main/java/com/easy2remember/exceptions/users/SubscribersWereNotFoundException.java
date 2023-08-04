package com.easy2remember.exceptions.users;

import org.springframework.dao.EmptyResultDataAccessException;

public class SubscribersWereNotFoundException extends EmptyResultDataAccessException {
    public SubscribersWereNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}