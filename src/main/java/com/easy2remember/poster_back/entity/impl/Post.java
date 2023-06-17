package com.easy2remember.poster_back.entity.impl;

import com.easy2remember.poster_back.entity.util.PostAbstractEntity;

import java.time.LocalDateTime;
import java.util.List;

public class Post extends PostAbstractEntity {

    private String timeZone;

    private String postName;

    private String postLink;

    private int priority;

    private boolean isExclusive;

    private List<Long> usersIdWithAccess;

    private boolean isHot;

    private byte[] previewImage;

    private Long postAnalyticsInfoId;

    private PostAnalyticsSnap postAnalyticsSnap;

    public Post() {
    }

    public Post(Long id, LocalDateTime createdAt, String timeZone, LocalDateTime lastChangedAt, String postName,
                String postLink, int priority, boolean isExclusive, List<Long> usersIdWithAccess,
                boolean isHot, byte[] previewImage, Long postAnalyticsInfoId, PostAnalyticsSnap postAnalyticsSnap) {

        super(id, createdAt, lastChangedAt);
        this.timeZone = timeZone;
        this.postName = postName;
        this.postLink = postLink;
        this.priority = priority;
        this.isExclusive = isExclusive;
        this.usersIdWithAccess = usersIdWithAccess;
        this.isHot = isHot;
        this.previewImage = previewImage;
        this.postAnalyticsInfoId = postAnalyticsInfoId;

        this.postAnalyticsSnap = postAnalyticsSnap;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public void setExclusive(boolean exclusive) {
        isExclusive = exclusive;
    }

    public List<Long> getUsersIdWithAccess() {
        return usersIdWithAccess;
    }

    public void setUsersIdWithAccess(List<Long> usersIdWithAccess) {
        this.usersIdWithAccess = usersIdWithAccess;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public byte[] getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(byte[] previewImage) {
        this.previewImage = previewImage;
    }

    public PostAnalyticsSnap getPostAnalyticsSnap() {
        return postAnalyticsSnap;
    }

    public void setPostAnalyticsSnap(PostAnalyticsSnap postAnalyticsSnap) {
        this.postAnalyticsSnap = postAnalyticsSnap;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Long getPostAnalyticsInfoId() {
        return postAnalyticsInfoId;
    }

    public void setPostAnalyticsInfoId(Long postAnalyticsInfoId) {
        this.postAnalyticsInfoId = postAnalyticsInfoId;
    }
}
