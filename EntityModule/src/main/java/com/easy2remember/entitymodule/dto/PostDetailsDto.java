package com.easy2remember.entitymodule.dto;

import java.time.LocalDateTime;

public record PostDetailsDto(LocalDateTime createdAt,
                            LocalDateTime lastChangedAt,
                            String description,
                            byte[] image) {}

