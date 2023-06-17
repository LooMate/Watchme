package com.easy2remember.poster_back.service;


import com.easy2remember.poster_back.dao.impl.JdbcUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferralService {

    private JdbcUserRepo jdbcUserRepo;

    @Autowired
    public ReferralService(JdbcUserRepo jdbcUserRepo) {
        this.jdbcUserRepo = jdbcUserRepo;
    }



    public Long proccessAndParseReferralCode(String referralCode) {
//        this.jdbcUserRepo.findIdByName(referralCode);

        System.out.println(1);
        return 1L;
    }

    private String addNewReferralToUserInvitor(String referralCode) {
        return null;
    }


}
