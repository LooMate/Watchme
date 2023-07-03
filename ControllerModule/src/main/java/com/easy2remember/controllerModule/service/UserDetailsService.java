package com.easy2remember.controllerModule.service;

import com.easy2remember.controllerModule.exceptions.users.DetailsTypeEnumException;
import com.easy2remember.controllerModule.exceptions.users.UserDetailsWasNotFoundException;
import com.easy2remember.controllerModule.exceptions.users.UserWasNotFoundException;
import com.easy2remember.daomodule.dao.impl.JdbcTelegramChannelDetailsRepo;
import com.easy2remember.daomodule.dao.impl.JdbcUserRepo;
import com.easy2remember.daomodule.dao.impl.JdbcUserTelegramDetailsRepo;
import com.easy2remember.daomodule.dao.impl.JdbcUserWebDetailsRepo;
import com.easy2remember.entitymodule.entity.impl.TelegramChannelDetails;
import com.easy2remember.entitymodule.entity.impl.UserTelegramDetails;
import com.easy2remember.entitymodule.entity.impl.UserWebDetails;
import com.easy2remember.entitymodule.enums.DetailsTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Repository
public class UserDetailsService {

    private JdbcUserRepo jdbcUserRepo;

    private JdbcUserWebDetailsRepo jdbcUserWebDetailsRepo;

    private JdbcUserTelegramDetailsRepo jdbcUserTelegramDetailsRepo;

    private JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo;

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public UserDetailsService(JdbcUserRepo jdbcUserRepo, JdbcUserWebDetailsRepo jdbcUserWebDetailsRepo,
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
        userWebDet.setCreatedAt(LocalDateTime.now());
        userWebDet.setLastChangedAt(LocalDateTime.now());
        if (this.jdbcUserWebDetailsRepo.save(userWebDet) == -1L) {
            throw new UserDetailsWasNotFoundException("user web details was NOT created", 1);
        }
        return 1;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int newUserTelegramDetails(UserTelegramDetails userTgDet) {
        userTgDet.setCreatedAt(LocalDateTime.now());
        userTgDet.setLastChangedAt(LocalDateTime.now());
        if (this.jdbcUserTelegramDetailsRepo.save(userTgDet) == -1L) {
            throw new UserDetailsWasNotFoundException("user telegram details was NOT created", 1);
        }
        return 1;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public int newTelegramChannelDetails(TelegramChannelDetails TgChDetails) {
        TgChDetails.setCreatedAt(LocalDateTime.now());
        TgChDetails.setLastChangedAt(LocalDateTime.now());
        if (this.jdbcTelegramChannelDetailsRepo.save(TgChDetails) == -1L) {
            throw new UserDetailsWasNotFoundException("telegram channel details was NOT created", 1);
        }
        return 1;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public UserWebDetails getUserWebDetailsByUserId(Long userId) {
        return this.jdbcUserWebDetailsRepo.findByUserId(userId).orElseThrow(() ->
                new UserDetailsWasNotFoundException("User Web Details was NOT found", 1));
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
}
