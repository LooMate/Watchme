package com.easy2remember.dto;

import java.time.LocalDateTime;

public record UserTelegramDetailsDto(LocalDateTime createdAt,
                                    LocalDateTime lastChangedAt,
                                    String timeZone,
                                    String firstName,
                                    String lastName,
                                    String username,
                                    Long chatId,
                                    boolean isReachableByPrivateForwards,
                                    boolean isPremium) {}
