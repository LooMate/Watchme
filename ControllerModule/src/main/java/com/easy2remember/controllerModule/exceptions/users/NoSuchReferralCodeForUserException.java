package com.easy2remember.controllerModule.exceptions.users;

public class NoSuchReferralCodeForUserException extends RuntimeException{
    public NoSuchReferralCodeForUserException(String message) {
        super(message);
    }
}
