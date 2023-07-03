package com.easy2remember.controllerModule.exceptions.posts;

import org.springframework.dao.EmptyResultDataAccessException;

public class NoCuchPostANalyticsInfoForSpecifiedPostException extends EmptyResultDataAccessException {
    public NoCuchPostANalyticsInfoForSpecifiedPostException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }
}
