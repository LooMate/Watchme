package com.easy2remember.entitymodule.dto;

import com.easy2remember.entitymodule.entity.impl.PostAnalyticsInfo;

public record PostAnalyticsDetailsDto(PostAnalyticsInfo pai, PostAnalyticsSnapDto[] pasArr) {}
