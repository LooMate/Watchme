package com.easy2remember.poster_back.entity.impl;

import com.easy2remember.poster_back.entity.util.UserAbstractEntity;
import com.easy2remember.poster_back.enums.InvitedFromType;

import java.time.LocalDateTime;
import java.util.List;

public class User extends UserAbstractEntity {

    private String username;

    private String email;

    private String usedReferralCode;

    private String referralCode;

    private List<Long> postIdList;

    private List<Long> inviteUsersIdList;

    private List<Long> subscribersIdList;

    private List<Long> subscribedPublisherIdList;

    private boolean isInvited;

    private InvitedFromType invitedFrom;

    private boolean isPremium;


    public User() {}

    public User(LocalDateTime createdAt,LocalDateTime lastChangedAt, String timeZone, String username, String email, String referralCode,
                List<Long> postIdList, List<Long> inviteUsersIdList, List<Long> subscribersIdList, boolean isInvited,
                InvitedFromType invitedFrom, boolean isPremium, String usedReferralCode,
                List<Long> subscribedPublisherIdList) {

        this(-1L, createdAt, lastChangedAt, timeZone, username, email, usedReferralCode, referralCode, postIdList, inviteUsersIdList,
                subscribersIdList, subscribedPublisherIdList, isInvited,invitedFrom,isPremium);
    }

    public User(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String timeZone, String username, String email, String usedReferralCode, String referralCode,
                List<Long> postIdList, List<Long> inviteUsersIdList, List<Long> subscribersIdList, List<Long> subscribedPublisherIdList, boolean isInvited, InvitedFromType invitedFrom, boolean isPremium) {
        super(id, createdAt, lastChangedAt, timeZone);
        this.username = username;
        this.email = email;
        this.usedReferralCode = usedReferralCode;
        this.referralCode = referralCode;
        this.postIdList = postIdList;
        this.inviteUsersIdList = inviteUsersIdList;
        this.subscribersIdList = subscribersIdList;
        this.subscribedPublisherIdList = subscribedPublisherIdList;
        this.isInvited = isInvited;
        this.invitedFrom = invitedFrom;
        this.isPremium = isPremium;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public List<Long> getInviteUsersIdList() {
        return inviteUsersIdList;
    }

    public void setInviteUsersIdList(List<Long> inviteUsersIdList) {
        this.inviteUsersIdList = inviteUsersIdList;
    }

    public List<Long> getSubscribersIdList() {
        return subscribersIdList;
    }

    public void setSubscribersIdList(List<Long> subscribersIdList) {
        this.subscribersIdList = subscribersIdList;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public InvitedFromType getInvitedFrom() {
        return invitedFrom;
    }

    public void setInvitedFrom(InvitedFromType invitedFrom) {
        this.invitedFrom = invitedFrom;
    }

    public String getUsedReferralCode() {
        return usedReferralCode;
    }

    public void setUsedReferralCode(String usedReferralCode) {
        this.usedReferralCode = usedReferralCode;
    }

    public List<Long> getSubscribedPublisherIdList() {
        return subscribedPublisherIdList;
    }

    public void setSubscribedPublisherIdList(List<Long> subscribedPublisherIdList) {
        this.subscribedPublisherIdList = subscribedPublisherIdList;
    }

    public List<Long> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<Long> postIdList) {
        this.postIdList = postIdList;
    }
}
