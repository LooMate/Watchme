package com.easy2remember.dto;


import com.easy2remember.enums.InvitedFromType;

import java.time.LocalDateTime;
import java.util.List;

public record UserDetailedDto(LocalDateTime createdAt,
                              LocalDateTime lastChangedAt,
                              String timeZone,
                              String username,
                              String email,
                              String usedReferralCode,
                              String referralCode,
                              List<Long> postsIdsList,
                              List<Long> invitedUsersIdsList,
                              List<Long> subscribersIdsList,
                              List<Long> subscribedPublishersIdsList,
                              boolean isInvited,
                              InvitedFromType invitedFrom,
                              boolean isPremium) {}


