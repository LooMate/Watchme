package com.easy2remember.dto;


import java.util.List;

public record UserDto(String username,
                      List<Long> postsIdsList,
                      List<Long> subscribersIdsList,
                      boolean isPremium) {}


