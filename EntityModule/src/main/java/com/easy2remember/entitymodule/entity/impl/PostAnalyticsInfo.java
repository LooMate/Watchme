package com.easy2remember.entitymodule.entity.impl;

import com.easy2remember.entitymodule.dto.PostAnalyticsInfoDto;
import com.easy2remember.entitymodule.entity.util.PostAbstractEntity;

import java.time.LocalDateTime;
import java.util.List;

public class PostAnalyticsInfo extends PostAbstractEntity {

    private boolean isActive;

    private int analyticsFrequencyInHour;

    private List<Long> postAnalyticsSnapsIdList;


    public PostAnalyticsInfo() {
    }

    public PostAnalyticsInfo(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, boolean isActive,
                             int analyticsFrequencyInHour, List<Long> postAnalyticsSnapsIdList) {
        super(id, createdAt, lastChangedAt);
        this.isActive = isActive;
        this.analyticsFrequencyInHour = analyticsFrequencyInHour;
        this.postAnalyticsSnapsIdList = postAnalyticsSnapsIdList;
    }

    public static PostAnalyticsInfoDto generateDto(PostAnalyticsInfo pai) {
        return new PostAnalyticsInfoDto(pai.getCreatedAt(),
                pai.getLastChangedAt(),
                pai.isActive(),
                pai.getAnalyticsFrequencyInHour(),
                pai.getPostAnalyticsSnapsIdList()
        );
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

    public List<Long> getPostAnalyticsSnapsIdList() {
        return postAnalyticsSnapsIdList;
    }

    public void setPostAnalyticsSnapsIdList(List<Long> postAnalyticsSnapsIdList) {
        this.postAnalyticsSnapsIdList = postAnalyticsSnapsIdList;
    }

    @Override
    public String toString() {
        return "PostAnalyticsInfo{" +
                "isActive=" + isActive +
                ", analyticsFrequencyInHour=" + analyticsFrequencyInHour +
                ", postAnalyticsSnapsIdList=" + postAnalyticsSnapsIdList +
                '}';
    }
}
