package com.easy2remember.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostAnalyticsInfoDto(LocalDateTime createdAt,
                                   LocalDateTime lastChangedAt,
                                   boolean isActive,
                                   int analyticsFrequencyInHour,
                                   List<Long> postAnalyticsSnapsIdList) {}
