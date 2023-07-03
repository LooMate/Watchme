package com.easy2remember.entitymodule.entity.impl;

import com.easy2remember.entitymodule.dto.UserDto;
import com.easy2remember.entitymodule.entity.util.UserAbstractEntity;
import com.easy2remember.entitymodule.enums.InvitedFromType;

import java.time.LocalDateTime;
import java.util.List;

public class User extends UserAbstractEntity {

    private String username;

    private String email;

    private String usedReferralCode;

    private String referralCode;

    private List<Long> postsIdsList;

    private List<Long> invitedUsersIdsList;

    private List<Long> subscribersIdsList;

    private List<Long> subscribedPublishersIdsList;

    private boolean isInvited;

    private InvitedFromType invitedFrom;

    private boolean isPremium;


    public User() {}

    public User(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String timeZone, String username, String email, String usedReferralCode, String referralCode,
                List<Long> postsIdsList, List<Long> invitedUsersIdsList, List<Long> subscribersIdsList, List<Long> subscribedPublishersIdsList, boolean isInvited, InvitedFromType invitedFrom, boolean isPremium) {
        super(id, createdAt, lastChangedAt, timeZone);
        this.username = username.trim();
        this.email = email.trim();
        this.usedReferralCode = usedReferralCode;
        this.referralCode = referralCode;
        this.postsIdsList = postsIdsList;
        this.invitedUsersIdsList = invitedUsersIdsList;
        this.subscribersIdsList = subscribersIdsList;
        this.subscribedPublishersIdsList = subscribedPublishersIdsList;
        this.isInvited = isInvited;
        this.invitedFrom = invitedFrom;
        this.isPremium = isPremium;
    }

    public static UserDto generateDto(User user) {
        return new UserDto(user.getCreatedAt(),
                user.getLastChangedAt(),
                user.getTimeZone(),
                user.getUsername(),
                user.getEmail(),
                user.getUsedReferralCode(),
                user.getReferralCode(),
                user.getPostsIdsList(),
                user.getInvitedUsersIdsList(),
                user.getSubscribersIdsList(),
                user.getSubscribedPublishersIdsList(),
                user.isInvited(),
                user.getInvitedFrom(),
                user.isPremium()
        );
    }

    public static UserDto[] generateDtoArray(User[] userArr) {
        var userDtoArr = new UserDto[userArr.length];
        for (int i = 0; i < userArr.length; i++) {
            userDtoArr[i] = generateDto(userArr[i]);
        }
        return userDtoArr;
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

    public List<Long> getInvitedUsersIdsList() {
        return invitedUsersIdsList;
    }

    public void setInvitedUsersIdsList(List<Long> invitedUsersIdsList) {
        this.invitedUsersIdsList = invitedUsersIdsList;
    }

    public List<Long> getSubscribersIdsList() {
        return subscribersIdsList;
    }

    public void setSubscribersIdsList(List<Long> subscribersIdsList) {
        this.subscribersIdsList = subscribersIdsList;
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

    public List<Long> getSubscribedPublishersIdsList() {
        return subscribedPublishersIdsList;
    }

    public void setSubscribedPublishersIdsList(List<Long> subscribedPublishersIdsList) {
        this.subscribedPublishersIdsList = subscribedPublishersIdsList;
    }

    public List<Long> getPostsIdsList() {
        return postsIdsList;
    }

    public void setPostsIdsList(List<Long> postsIdsList) {
        this.postsIdsList = postsIdsList;
    }

    @Override
    public String toString() {
        return super.toString() + "\nUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", usedReferralCode='" + usedReferralCode + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", postIdList=" + postsIdsList +
                ", inviteUsersIdList=" + invitedUsersIdsList +
                ", subscribersIdList=" + subscribersIdsList +
                ", subscribedPublisherIdList=" + subscribedPublishersIdsList +
                ", isInvited=" + isInvited +
                ", invitedFrom=" + invitedFrom +
                ", isPremium=" + isPremium +
                '}';
    }
}
