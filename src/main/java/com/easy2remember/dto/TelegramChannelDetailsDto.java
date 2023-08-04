package com.easy2remember.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TelegramChannelDetailsDto(LocalDateTime createdAt,
                                       LocalDateTime lastChangedAt,
                                       String timeZone,
                                       String channelName,
                                       String channelBio,
                                       String channelLink,
                                       Long numberOfMembers,
                                       String typeOfChat,
                                       List<Long> adminsOfChannelIds) {
}
