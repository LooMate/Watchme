package com.easy2remember.controllerModule.service;


import com.easy2remember.controllerModule.exceptions.users.*;
import com.easy2remember.daomodule.dao.impl.JdbcTelegramChannelDetailsRepo;
import com.easy2remember.daomodule.dao.impl.JdbcUserRepo;
import com.easy2remember.entitymodule.entity.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.IntSupplier;


@Service
public class UserService {

    private JdbcUserRepo jdbcUserRepo;
    private JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo;

    private NotificationService notificationService;

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public UserService(JdbcUserRepo jdbcUserRepo, JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo,
                       NotificationService notificationService) {
        this.jdbcUserRepo = jdbcUserRepo;
        this.jdbcTelegramChannelDetailsRepo = jdbcTelegramChannelDetailsRepo;
        this.notificationService = notificationService;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int newUser(User user) {
        long userId = -1;

        user.setReferralCode(user.getUsername());

        Long invitorId = null;

        if (user.getUsedReferralCode() != null && !user.getUsedReferralCode().trim().isBlank()) {

            try {
                invitorId = this.jdbcUserRepo.findIdByName(user.getUsedReferralCode().trim());
            } catch (EmptyResultDataAccessException e) {
                throw new NoSuchReferralCodeForUserException("Referral code is not valid");
            }

            user.setInvited(true);
            try {
                userId = jdbcUserRepo.save(user);
            } catch (DuplicateKeyException e) {
                throw new UserAlreadyExistsException("User with this name is already exists");
            }

            if (userId == -1) {
                throw new UserWasNotSavedException("User was NOT saved", 1);
            }
            jdbcUserRepo.saveReferralIdToInvitor(userId, invitorId);
        } else {
            try {
                jdbcUserRepo.save(user);
            } catch (DuplicateKeyException e) {
                throw new UserAlreadyExistsException("User with this name is already exists");
            }
        }
        return 1;
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public User getUserByName(String username) {
        try {
            return this.jdbcUserRepo.findByUsername(username)
                    .orElseThrow(() -> new UserWasNotFoundException("user was NOT found", 1));
        } catch (EmptyResultDataAccessException e) {
            throw new UserWasNotFoundException("user was NOT found", 1);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public Long getUserIdByName(String username) {
        return this.jdbcUserRepo.findIdByName(username);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public User[] getAllPublisherSubscribers(String publisherName) {
        Long publisherId = null;

        try {
            publisherId = this.jdbcUserRepo.findIdByName(publisherName);
        } catch (EmptyResultDataAccessException e) {
            throw new PublisherWasNotFoundException("Publisher does NOT exists", 1);
        }

        var users = this.jdbcUserRepo.findAllPublisherSubscribersById(publisherId);
        if (users == null) {
            throw new SubscribersWereNotFoundException("List of subscribers was NOT found", 1);
        }
        return users;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public int updateUser(User updatedUser, String username) { //it will be replaced with JWT
        updatedUser.setPostsIdsList(new ArrayList<>());
        updatedUser.setLastChangedAt(LocalDateTime.now());
        try {
            return this.jdbcUserRepo.updateById(updatedUser, getUserIdByName(username));
        } catch (EmptyResultDataAccessException e) {
            throw new UserWasNotUpdatedException("User " + updatedUser.getUsername() + " was NOT found", 1);
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int subscribeUserToPublisher(String publisherName, String subscriberName) {
        Long pubId = getUserIdByName(publisherName);

        if (pubId == null) {
            throw new PublisherWasNotFoundException("Publisher was NOT found", 1);
        }

        this.subscriberHelper(
                () -> this.jdbcUserRepo.saveSubscriberToPublisherById(getUserIdByName(subscriberName), pubId),
                () -> this.jdbcUserRepo.savePublisherIdToSubscriber(pubId, getUserIdByName(subscriberName)),
                new CouldNotSaveSubscriberToPublisherException("Subscriber's id was NOT saved to the publisher"),
                new CouldNotSavePublisherIdToSubscriberException("Publisher's id was NOT saved to the subscriber")
        );
        return 1;
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void unsubscribeUserFromPublisher(String publisherName, String subscriberName) {
        Long pubId = getUserIdByName(publisherName);

        if (pubId == null) {
            throw new PublisherWasNotFoundException("Publisher was NOT found", 1);
        }

        this.subscriberHelper(
                () -> this.jdbcUserRepo.removeSubscriberFromPublisherById(getUserIdByName(subscriberName), pubId),
                () -> this.jdbcUserRepo.removePublisherIdFromSubscriber(pubId, getUserIdByName(subscriberName)),
                new CouldNotUnsubscribeUserFromPublisherException("Subscriber's id was NOT removed from publisher"),
                new CouldNotRemoveSubscriptionFromUserException("Publisher's id was NOT removed from subscriber")
        );
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.REPEATABLE_READ)
    public void subscriberHelper(IntSupplier methodCall1, IntSupplier methodCall2,
                                 RuntimeException exceptionForMethodCall1,
                                 RuntimeException exceptionForMethodCall2) {

        if (methodCall1.getAsInt() == 0) {
            throw exceptionForMethodCall1;
        }
        if (methodCall2.getAsInt() == 0) {
            throw exceptionForMethodCall2;
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int removeUser(String username) {
        if (this.jdbcUserRepo.deleteById(getUserIdByName(username)) == 1) {
            return 1;
        } else {
            throw new UserWasNotDeletedException("User " + username + " was NOT deleted", 1);
        }
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
