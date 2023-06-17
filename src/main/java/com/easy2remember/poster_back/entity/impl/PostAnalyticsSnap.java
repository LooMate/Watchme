package com.easy2remember.poster_back.entity.impl;

import com.easy2remember.poster_back.entity.util.PostAbstractEntity;

import java.time.LocalDateTime;
import java.util.List;

public class PostAnalyticsSnap extends PostAbstractEntity {

    private Long numOfSpreads;

    private Long viewedNum;

    private Long viewedByReferralNum;

    private List<Long> viewersId;

    private List<Long> refViewersId;

    private Long postRates;

    public PostAnalyticsSnap() {
    }

    public PostAnalyticsSnap(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, Long numOfSpreads,
                             Long viewedNum, Long viewedByReferralNum, List<Long> viewersId, List<Long> refViewersId,
                             Long postRates) {
        super(id, createdAt,lastChangedAt);
        this.numOfSpreads = numOfSpreads;
        this.viewedNum = viewedNum;
        this.viewedByReferralNum = viewedByReferralNum;
        this.viewersId = viewersId;
        this.refViewersId = refViewersId;
        this.postRates = postRates;
    }

    public Long getNumOfSpreads() {
        return numOfSpreads;
    }

    public void setNumOfSpreads(Long numOfSpreads) {
        this.numOfSpreads = numOfSpreads;
    }

    public Long getViewedNum() {
        return viewedNum;
    }

    public void setViewedNum(Long viewedNum) {
        this.viewedNum = viewedNum;
    }

    public Long getViewedByReferralNum() {
        return viewedByReferralNum;
    }

    public void setViewedByReferralNum(Long viewedByReferralNum) {
        this.viewedByReferralNum = viewedByReferralNum;
    }

    public List<Long> getViewersId() {
        return viewersId;
    }

    public void setViewersId(List<Long> viewersId) {
        this.viewersId = viewersId;
    }

    public List<Long> getRefViewersId() {
        return refViewersId;
    }

    public void setRefViewersId(List<Long> refViewersId) {
        this.refViewersId = refViewersId;
    }

    public Long getPostRates() {
        return postRates;
    }

    public void setPostRates(Long postRates) {
        this.postRates = postRates;
    }
}
