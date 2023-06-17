package com.easy2remember.poster_back.controller;


import com.easy2remember.poster_back.entity.impl.User;
import com.easy2remember.poster_back.enums.InvitedFromType;
import com.easy2remember.poster_back.service.NotificationService;
import com.easy2remember.poster_back.service.ReferralService;
import com.easy2remember.poster_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequestMapping("")
@RestController
public class UserController {

    private UserService userService;
    private ReferralService referralService;
    private NotificationService notificationService;


    @Autowired
    public UserController(UserService userService, ReferralService referralService, NotificationService notificationService) {
        this.userService = userService;
        this.referralService = referralService;
        this.notificationService = notificationService;
    }


    public User[] showAllPublisherSubscribers(Long publisherId, String jwt) {
        return null;
    }

    @GetMapping("new")
    public String createUser(User user, String referralCode) {
        userService.newUser(new User(LocalDateTime.now(),LocalDateTime.now(), "warszaw", "myname",
                "myEmail@gmail.com", "myname", new ArrayList(), new ArrayList(), new ArrayList(),
                true, InvitedFromType.GMAIL, false, "usedReferralCode",
                new ArrayList()), referralCode);
        return "";
    }

    public User showUser(Long userId) {
        return null;
    }

    public String updateUser(User updatedUser, String jwt) {
        return "";
    }

    public String deleteUser(String jwt){
        return "";
    }

    public String createReferral(User userInvitorId) {
        return "";
    }

    public String subscribeToPublisher(String publisherName, String jwt) {
        return "";
    }

    public String unsubscribeToPublisher(String publisherName, String jwt) {
        return "";
    }

}
