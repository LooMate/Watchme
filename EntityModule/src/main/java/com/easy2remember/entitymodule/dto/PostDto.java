package com.easy2remember.entitymodule.dto;

import java.time.LocalDateTime;

public record PostDto(LocalDateTime createdAt,
                      LocalDateTime lastChangedAt,
                      String postName,
                      String postLink,
                      int priority,
                      boolean isExclusive,
                      boolean isHot,
                      byte[] previewImage,
                      PostAnalyticsSnapDto postAnalyticsSnap) {
}
