package com.easy2remember.dto;

import java.time.LocalDateTime;

public record PostDto(Long id,
        LocalDateTime createdAt,
                      LocalDateTime lastChangedAt,
                      String postName,
                      String postLink,
                      int priority,
                      boolean isExclusive,
                      boolean isHot,
                      byte[] previewImage,
                      PostAnalyticsSnapDto postAnalyticsSnap) {
}
