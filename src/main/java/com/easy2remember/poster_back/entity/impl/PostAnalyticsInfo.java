package com.easy2remember.poster_back.entity.impl;

import com.easy2remember.poster_back.entity.util.PostAbstractEntity;

import java.time.LocalDateTime;

public class PostAnalyticsInfo extends PostAbstractEntity {

    private boolean isActive;

    private int analyticsFrequencyInHour;

    private Long postId;

    public PostAnalyticsInfo() {
    }

    public PostAnalyticsInfo(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, boolean isActive,
                             int analyticsFrequencyInHour, Long postId) {
        super(id, createdAt, lastChangedAt);
        this.isActive = isActive;
        this.analyticsFrequencyInHour = analyticsFrequencyInHour;
        this.postId = postId;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getAnalyticsFrequencyInHour() {
        return analyticsFrequencyInHour;
    }

    public void setAnalyticsFrequencyInHour(int analyticsFrequencyInHour) {
        this.analyticsFrequencyInHour = analyticsFrequencyInHour;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
