package com.easy2remember.exceptions.users;

public class NoSuchReferralCodeForUserException extends RuntimeException{
    public NoSuchReferralCodeForUserException(String message) {
        super(message);
    }
}
