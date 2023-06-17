package com.easy2remember.poster_back.service;

import com.easy2remember.poster_back.dao.impl.JdbcTelegramChannelDetailsRepo;
import com.easy2remember.poster_back.dao.impl.JdbcUserRepo;
import com.easy2remember.poster_back.dao.impl.JdbcUserTelegramDetailsRepo;
import com.easy2remember.poster_back.dao.impl.JdbcUserWebDetailsRepo;
import com.easy2remember.poster_back.entity.impl.TelegramChannelDetails;
import com.easy2remember.poster_back.entity.impl.UserTelegramDetails;
import com.easy2remember.poster_back.entity.impl.UserWebDetails;
import com.easy2remember.poster_back.enums.DataTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UserDetailsService {


    private JdbcUserRepo jdbcUserRepo;

    private JdbcUserWebDetailsRepo jdbcUserWebDetailsRepo;

    private JdbcUserTelegramDetailsRepo jdbcUserTelegramDetailsRepo;

    private JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo;


    @Autowired
    public UserDetailsService(JdbcUserWebDetailsRepo jdbcUserWebDetailsRepo,
                              JdbcUserTelegramDetailsRepo jdbcUserTelegramDetailsRepo,
                              JdbcTelegramChannelDetailsRepo jdbcTelegramChannelDetailsRepo) {
        this.jdbcUserWebDetailsRepo = jdbcUserWebDetailsRepo;
        this.jdbcUserTelegramDetailsRepo = jdbcUserTelegramDetailsRepo;
        this.jdbcTelegramChannelDetailsRepo = jdbcTelegramChannelDetailsRepo;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int newUserWebDet(UserWebDetails userWebDet) {
        if (this.jdbcUserWebDetailsRepo.save(userWebDet) == -1L) {
            throw new EmptyResultDataAccessException("user web details can't be created", 1);
        }
        return 1;
    }

    public int newUserTelegramDetails(UserTelegramDetails userTgDet) {
        if (this.jdbcUserTelegramDetailsRepo.save(userTgDet) == -1L) {
            throw new EmptyResultDataAccessException("user telegram details can't be created", 1);
        }
        return 1;
    }

    public int newTelegramChannelDetails(TelegramChannelDetails TgChDetails) {
        if (this.jdbcTelegramChannelDetailsRepo.save(TgChDetails) == -1L) {
            throw new EmptyResultDataAccessException("telegram channel details can't be created", 1);
        }
        return 1;
    }


    public UserWebDetails getUserWebDet(Long userWebDetId) {
        return this.jdbcUserWebDetailsRepo.findById(userWebDetId).orElseGet(() -> null);
    }

    public UserTelegramDetails getUserTelegramDetails(Long userTgDetId) {
        return this.jdbcUserTelegramDetailsRepo.findById(userTgDetId).orElseGet(() -> null);
    }

    public TelegramChannelDetails getTelegramChannelDetails(Long tgChDetailsId) {
        return this.jdbcTelegramChannelDetailsRepo.findById(tgChDetailsId).orElseGet(() -> null);
    }


    public int updateUserWebDet(UserWebDetails updatedWebDet) {
        return this.jdbcUserWebDetailsRepo.updateById(updatedWebDet, updatedWebDet.getId());
    }

    public int updateUserTelegramDetails(UserTelegramDetails updatedTgDet) {
        return this.jdbcUserTelegramDetailsRepo.updateById(updatedTgDet, updatedTgDet.getId());
    }

    public int updateTelegramChannelDetails(TelegramChannelDetails updatedTgChDetails) {
        return this.jdbcTelegramChannelDetailsRepo.updateById(updatedTgChDetails, updatedTgChDetails.getId());
    }


    public int recollectDetails(String username, DataTypeEnum recollectType) {
        return 1;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public int removeDetails(String username, DataTypeEnum deleteType) {
        Long userId = this.jdbcUserRepo.findIdByName(username);
        if(userId == null) {
            throw new EmptyResultDataAccessException("user can't be found", 1);
        }

        switch (deleteType) {
            case USER_WEB_DETAILS -> {
                return this.jdbcUserWebDetailsRepo.deleteById(userId);
            }
            case USER_TELEGRAM_DETAILS -> {
                return this.jdbcUserTelegramDetailsRepo.deleteById(userId);
            }
            case TELEGRAM_CHANNEL_DETAILS -> {
                return this.jdbcTelegramChannelDetailsRepo.deleteById(userId);
            }

        }
        return -1;
    }

}
