package com.easy2remember.dto;

import com.easy2remember.entity.impl.details.PostAnalyticsInfo;

public record PostAnalyticsDetailsDto(PostAnalyticsInfo pai, PostAnalyticsSnapDto[] pasArr) {}
