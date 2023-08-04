package com.easy2remember.dto.auth;

public record UserRegistrationRequest(String timeZone, String username, char[] password, String email,
                                      String usedReferralCode, String invitedFrom) {}
