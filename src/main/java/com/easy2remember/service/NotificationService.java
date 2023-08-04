package com.easy2remember.service;


import com.easy2remember.dao.impl.JdbcUserRepo;
import com.easy2remember.entity.impl.main.Post;
import com.easy2remember.enums.NotificationTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class NotificationService {

    private JdbcUserRepo jdbcUserRepo;


    @Autowired
    public NotificationService(JdbcUserRepo jdbcUserRepo) {
        this.jdbcUserRepo = jdbcUserRepo;
    }


    public int sendPostToAllSubscribers(Post post, LocalDateTime dateTime, NotificationTypeEnum[] notificationType) {
        System.out.println("notification have been sent to users by: " + Arrays.toString(notificationType));
        return 1;
    }

    private int sendPostToSubscribersEmail(Post post, LocalDateTime date, String timeZone) {
        return 1;
    }

    private int sendPostToTelegramChannel(Post post, LocalDateTime date, String timeZone) {
        return 1;
    }

    private int sendPostToSubscribers(Post post, LocalDateTime date, String timeZone) {
        return 1;
    }


}
