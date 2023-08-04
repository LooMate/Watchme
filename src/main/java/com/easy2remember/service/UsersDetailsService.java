package com.easy2remember.service;

import com.easy2remember.exceptions.users.DetailsTypeEnumException;
import com.easy2remember.exceptions.users.UserDetailsWasNotFoundException;
import com.easy2remember.exceptions.users.UserIdDoesNotMatchLoggedUserException;
import com.easy2remember.security.CustomUserDetails;
import com.easy2remember.dao.impl.JdbcTelegramChannelDetailsRepo;
import com.easy2remember.dao.impl.JdbcUserRepo;
import com.easy2remember.dao.impl.JdbcUserTelegramDetailsRepo;
import com.easy2remember.dao.impl.JdbcUserWebDetailsRepo;
import com.easy2remember.entity.impl.details.TelegramChannelDetails;
import com.easy2remember.entity.impl.details.UserTelegramDetails;
import com.easy2remember.entity.impl.details.UserWebDetails;
import com.easy2remember.enums.DetailsTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;


@Repository
public class UsersDetailsService {

    private JdbcUserRepo jdbcUserRepo;

    private JdbcUserWebDetailsRepo jdbcUserWebDetailsRepo;

    private JdbcUserTelegramDetailsRepo jdbcUserTelegramDetailsRepo;

    private JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo;

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public UsersDetailsService(JdbcUserRepo jdbcUserRepo, JdbcUserWebDetailsRepo jdbcUserWebDetailsRepo,
                               JdbcUserTelegramDetailsRepo jdbcUserTelegramDetailsRepo,
                               JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo) {
        this.jdbcUserRepo = jdbcUserRepo;
        this.jdbcUserWebDetailsRepo = jdbcUserWebDetailsRepo;
        this.jdbcUserTelegramDetailsRepo = jdbcUserTelegramDetailsRepo;
        this.jdbcTelegramChannelDetailsRepo = jdbcTelegramChannelDetailsRepo;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public int newUserWebDetails(UserWebDetails userWebDet) {
        //check whether logged-in user matches given user id
        this.isUserAuthentic(userWebDet.getUserId());

        userWebDet.setCreatedAt(LocalDateTime.now());
        userWebDet.setLastChangedAt(LocalDateTime.now());
        if (this.jdbcUserWebDetailsRepo.save(userWebDet) == -1L) {
            throw new UserDetailsWasNotFoundException("user web details was NOT created", 1);
        }
        return 1;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int newUserTelegramDetails(UserTelegramDetails userTgDet) {
        //check whether logged-in user matches given user id
        this.isUserAuthentic(userTgDet.getUserId());

        userTgDet.setCreatedAt(LocalDateTime.now());
        userTgDet.setLastChangedAt(LocalDateTime.now());
        if (this.jdbcUserTelegramDetailsRepo.save(userTgDet) == -1L) {
            throw new UserDetailsWasNotFoundException("user telegram details was NOT created", 1);
        }
        return 1;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int newTelegramChannelDetails(TelegramChannelDetails tgChDetails) {
        //check whether logged-in user matches given user id
        this.isUserAuthentic(tgChDetails.getUserId());

        tgChDetails.setCreatedAt(LocalDateTime.now());
        tgChDetails.setLastChangedAt(LocalDateTime.now());
        if (this.jdbcTelegramChannelDetailsRepo.save(tgChDetails) == -1L) {
            throw new UserDetailsWasNotFoundException("telegram channel details was NOT created", 1);
        }
        return 1;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public UserWebDetails getUserWebDetailsByUserId(Long userId) {
        //check whether logged-in user matches given user id
        this.isUserAuthentic(userId);

        try {
            return this.jdbcUserWebDetailsRepo.findByUserId(userId).orElseThrow(() ->
                    new UserDetailsWasNotFoundException("User Web Details was NOT found", 1));
        } catch (EmptyResultDataAccessException e) {
            throw new UserDetailsWasNotFoundException("User Web Details was NOT found", 1);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    public UserTelegramDetails getUserTelegramDetailsByUserId(Long userId) {
        return this.jdbcUserTelegramDetailsRepo.findByUserId(userId).orElseThrow(() ->
                new UserDetailsWasNotFoundException("User Telegram Details was NOT found", 1));
    }
    //----------------------------------------------------------------------------------------------------------------------
    public TelegramChannelDetails getTelegramChannelDetailsByUserId(Long userId) {
        return this.jdbcTelegramChannelDetailsRepo.findByUserId(userId).orElseThrow(() ->
                new UserDetailsWasNotFoundException("Telegram Channel Details was NOT found", 1));
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public int updateUserWebDetails(UserWebDetails updatedWebDet) {
        updatedWebDet.setLastChangedAt(LocalDateTime.now());
        return this.jdbcUserWebDetailsRepo.updateById(updatedWebDet, updatedWebDet.getId());
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int updateUserTelegramDetails(UserTelegramDetails updatedTgDet) {
        updatedTgDet.setLastChangedAt(LocalDateTime.now());
        return this.jdbcUserTelegramDetailsRepo.updateById(updatedTgDet, updatedTgDet.getId());
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int updateTelegramChannelDetails(TelegramChannelDetails updatedTgChDetails) {
        updatedTgChDetails.setLastChangedAt(LocalDateTime.now());
        return this.jdbcTelegramChannelDetailsRepo.updateById(updatedTgChDetails, updatedTgChDetails.getId());
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public int recollectDetails(Long userId, String recollectType) {
//        DetailsTypeEnum.valueOf(recollectType);
        return 1;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public int removeDetailsByUserId(Long userId, String deleteTypeString) {
        DetailsTypeEnum deleteType = null;
        try {
            deleteType = DetailsTypeEnum.valueOf(deleteTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DetailsTypeEnumException("No such data type enum");
        }

        return switch (deleteType) {
            case USER_WEB_DETAILS ->            this.jdbcUserWebDetailsRepo.deleteById(userId);
            case USER_TELEGRAM_DETAILS ->       this.jdbcUserTelegramDetailsRepo.deleteById(userId);
            case TELEGRAM_CHANNEL_DETAILS ->    this.jdbcTelegramChannelDetailsRepo.deleteById(userId);
        };
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    private void isUserAuthentic(Long userId) {
        if(Objects.equals(userId, ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUserId())) {
            throw new UserIdDoesNotMatchLoggedUserException("User Id does not match logged User");
        }
    }

}
