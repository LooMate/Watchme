package com.easy2remember.poster_back.controller;

import com.easy2remember.poster_back.entity.impl.TelegramChannelDetails;
import com.easy2remember.poster_back.entity.impl.UserTelegramDetails;
import com.easy2remember.poster_back.entity.impl.UserWebDetails;
import com.easy2remember.poster_back.enums.DataTypeEnum;
import com.easy2remember.poster_back.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserAnalyticsController {

    private UserDetailsService userDetailsService;

    @Autowired
    public UserAnalyticsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createUserWebDet(UserWebDetails userWebDet) {
        return null;
    }

    public String createUserTelegramDetails(UserTelegramDetails userTgDet) {
        return null;
    }

    public String createTelegramChannelDetails(TelegramChannelDetails TgChDetails) {
        return null;
    }


    public UserWebDetails showUserWebDet(Long userWebDetId) {
        return null;
    }

    public UserTelegramDetails showUserTelegramDetails(Long userTgDetId) {
        return null;
    }

    public TelegramChannelDetails showTelegramChannelDetails(Long tgChDetailsId) {
        return null;
    }


    public String updateUserWebDet(UserWebDetails updatedWebDet, String jwt) {
        return null;
    }

    public String updateUserTelegramDetails(UserWebDetails updatedTgDet, String jwt) {
        return null;
    }

    public String updateTelegramChannelDetails(UserWebDetails updatedTgChDetails, String jwt) {
        return null;
    }


    public String recollectDetails(String username, DataTypeEnum recollectType, String jwt) {
        return null;
    }

    public String deleteDetails(String username, DataTypeEnum deleteType, String jwt) {
        return null;
    }
}
