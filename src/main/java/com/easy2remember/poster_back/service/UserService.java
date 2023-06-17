package com.easy2remember.poster_back.service;


import com.easy2remember.poster_back.dao.impl.JdbcTelegramChannelDetailsRepo;
import com.easy2remember.poster_back.dao.impl.JdbcUserRepo;
import com.easy2remember.poster_back.entity.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private JdbcUserRepo jdbcUserRepo;
    private JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo;

    private ReferralService referralService;
    private NotificationService notificationService;


    @Autowired
    public UserService(JdbcUserRepo jdbcUserRepo, JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo,
                       ReferralService referralService, NotificationService notificationService) {
        this.jdbcUserRepo = jdbcUserRepo;
        this.jdbcTelegramChannelDetailsRepo = jdbcTelegramChannelDetailsRepo;
        this.referralService = referralService;
        this.notificationService = notificationService;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
// FIXME: 6/9/2023 change implementation of referral service
    public int newUser(User user, String referralCode) {

        Long userId = jdbcUserRepo.save(user);

        Long invitorId = this.referralService.proccessAndParseReferralCode(referralCode);
        if (invitorId != null) {
            return jdbcUserRepo.saveReferralIdToInvitor(userId, invitorId);
        }
        return -1;
    }

    public User getUser(Long userId) {
        return this.jdbcUserRepo.findById(userId).orElseGet(() -> null);
    }

    public int updateUser(User updatedUser, String username) {
        return this.jdbcUserRepo.updateById(updatedUser, getUserIdByName(username));// FIXME: 6/8/2023 implement jwt
    }

    public int removeUser(String username) {
        return this.jdbcUserRepo.deleteById(getUserIdByName(username)); // FIXME: 6/8/2023 implement jwt
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
            rollbackFor = EmptyResultDataAccessException.class)
    public int subscribeUserToPublisher(String publisherName, String subscriberName) {     // FIXME: 6/8/2023 implement jwt
        Long pubId = this.jdbcUserRepo.findIdByName(publisherName);

        if (pubId == null) {
            return -1;
        }
        if(this.jdbcUserRepo.saveSubscriberToPublisherById(getUserIdByName(subscriberName), pubId) == 0) {
            throw new EmptyResultDataAccessException("Subscriber's id wasn't saved to the publisher", 1);
        }

        if(this.jdbcUserRepo.savePublisherIdToSubscriber(pubId, getUserIdByName(subscriberName)) == 0) {
            throw new EmptyResultDataAccessException("Publisher's id wasn't saved to the subscriber", 1);
        }

        return 1;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
            rollbackFor = EmptyResultDataAccessException.class)
    public int unsubscribeUserFromPublisher(String publisherName, String subscriberName) {// FIXME: 6/8/2023 implement jwt


        Long pubId = this.jdbcUserRepo.findIdByName(publisherName);

        if(pubId == null) {
            return -1;
        }

        if(this.jdbcUserRepo.removeSubscriberFromPublisherById(getUserIdByName(subscriberName),pubId) == 0) {
            throw new EmptyResultDataAccessException("Subscriber's id wasn't removed from publisher", 1);
        }

        if(this.jdbcUserRepo.removePublisherIdFromSubscriber(pubId, getUserIdByName(subscriberName)) == 0) {
            throw new EmptyResultDataAccessException("Publisher's id wasn't removed from subscriber", 1);
        }

        return 1;
    }

    public User[] getAllPublisherSubscribers(Long publisherId) { // FIXME: 6/8/2023 implement jwt
        return this.jdbcUserRepo.findAllPublisherSubscribersById(publisherId);
    }

    // to be used only in UserService class
    public Long getUserIdByName(String username) {
        Long userId = this.jdbcUserRepo.findIdByName(username);
        if(userId == null) {
            throw new EmptyResultDataAccessException("user can't be found", 1);
        }
        return userId;
    }
//    private int sendConfirmationToEmail(Long userId) {
//        return -1;
//    }
}
