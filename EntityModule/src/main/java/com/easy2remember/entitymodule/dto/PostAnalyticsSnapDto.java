package com.easy2remember.entitymodule.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostAnalyticsSnapDto(
        LocalDateTime createdAt,
        LocalDateTime lastChangedAt,
        Long numOfSpreads,
        Long viewedNum,
        Long viewedByReferralNum,
        List<Long> viewersId, //?????????
        List<Long> refViewersId,//?????????
        Long postRates) {
}

