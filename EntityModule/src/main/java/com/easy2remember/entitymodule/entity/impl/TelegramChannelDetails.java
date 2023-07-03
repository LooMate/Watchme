package com.easy2remember.entitymodule.entity.impl;

import com.easy2remember.entitymodule.dto.TelegramChannelDetailsDto;
import com.easy2remember.entitymodule.entity.util.UserDetailsAbstractEntity;

import java.time.LocalDateTime;
import java.util.List;

public class TelegramChannelDetails extends UserDetailsAbstractEntity {

    private String channelName;

    private String channelBio;

    private String channelLink;

    private Long numberOfMembers;

    private String typeOfChat;

    private List<Long> adminsOfChannelIds;

    private Long userId;

    public TelegramChannelDetails() {
    }

    public TelegramChannelDetails(Long id, LocalDateTime createdAt, LocalDateTime lastChangedAt, String timeZone, String channelName, String channelBio,
                                  String channelLink, Long numberOfMembers, String typeOfChat,
                                  List<Long> adminsOfChannelIds, Long userId) {
        super(id, createdAt, lastChangedAt, timeZone);
        this.channelName = channelName;
        this.channelBio = channelBio;
        this.channelLink = channelLink;
        this.numberOfMembers = numberOfMembers;
        this.typeOfChat = typeOfChat;
        this.adminsOfChannelIds = adminsOfChannelIds;
        this.userId = userId;
    }

    public static TelegramChannelDetailsDto generateDto(TelegramChannelDetails telegramChannelDetails) {
        return new TelegramChannelDetailsDto(telegramChannelDetails.getCreatedAt(),
                telegramChannelDetails.getLastChangedAt(),
                telegramChannelDetails.getTimeZone(),
                telegramChannelDetails.getChannelName(),
                telegramChannelDetails.getChannelBio(),
                telegramChannelDetails.getChannelLink(),
                telegramChannelDetails.getNumberOfMembers(),
                telegramChannelDetails.getTypeOfChat(),
                telegramChannelDetails.getAdminsOfChannelIds());
    }




    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelBio() {
        return channelBio;
    }

    public void setChannelBio(String channelBio) {
        this.channelBio = channelBio;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    public Long getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(Long numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getTypeOfChat() {
        return typeOfChat;
    }

    public void setTypeOfChat(String typeOfChat) {
        this.typeOfChat = typeOfChat;
    }

    public List<Long> getAdminsOfChannelIds() {
        return adminsOfChannelIds;
    }

    public void setAdminsOfChannelIds(List<Long> adminsOfChannelIds) {
        this.adminsOfChannelIds = adminsOfChannelIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
