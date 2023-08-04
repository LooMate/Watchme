package com.easy2remember.dto;


import java.time.LocalDateTime;

public record UserWebDetailsDto(LocalDateTime createdAt,
                                LocalDateTime lastChangedAt,
                                String userBrowser,
                                String browserLanguage,
                                String operationSystem,
                                String location,
                                String screenSizeAndColorDepth,
                                int timeZoneOffset,
                                String timeZone,
                                String webVendorAndRenderGpu,
                                String cpuName,
                                int cpuCoreNum,
                                int deviceMemory,
                                boolean touchSupport,
                                boolean adBlockerUsed,
                                Long userId) {}
