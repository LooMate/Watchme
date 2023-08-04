package com.easy2remember.entity.impl.details;

import com.easy2remember.dto.UserTelegramDetailsDto;
import com.easy2remember.entity.util.UserDetailsAbstractEntity;

import java.time.LocalDateTime;

public class UserTelegramDetails extends UserDetailsAbstractEntity {

    private String firstName;

    private String lastName;

    private String username;

    private Long chatId;

    private boolean isReachableByPrivateForwards;

    private boolean isPremium;

    private Long userId;

    public UserTelegramDetails() {
    }

    public UserTelegramDetails(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String timeZone, String firstName, String lastName, String username,
                               Long chatId, boolean isReachableByPrivateForwards, boolean isPremium,
                               Long userId) {
        super(id, createdAt, lastChangedAt, timeZone);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.chatId = chatId;
        this.isReachableByPrivateForwards = isReachableByPrivateForwards;
        this.isPremium = isPremium;
        this.userId = userId;
    }

    public static UserTelegramDetailsDto generateDto(UserTelegramDetails userTelegramDetails) {
        return new UserTelegramDetailsDto(userTelegramDetails.getCreatedAt(),
                userTelegramDetails.getLastChangedAt(),
                userTelegramDetails.getTimeZone(),
                userTelegramDetails.getFirstName(),
                userTelegramDetails.getLastName(),
                userTelegramDetails.getUsername(),
                userTelegramDetails.getChatId(),
                userTelegramDetails.isReachableByPrivateForwards(),
                userTelegramDetails.isPremium()
        );
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isReachableByPrivateForwards() {
        return isReachableByPrivateForwards;
    }

    public void setReachableByPrivateForwards(boolean reachableByPrivateForwards) {
        isReachableByPrivateForwards = reachableByPrivateForwards;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserTelegramDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", chatId=" + chatId +
                ", isReachableByPrivateForwards=" + isReachableByPrivateForwards +
                ", isPremium=" + isPremium +
                ", userId=" + userId +
                '}';
    }
}
