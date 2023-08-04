package com.easy2remember.dto.auth;

public record UserLoginRequest(String username, byte[] password) {}
