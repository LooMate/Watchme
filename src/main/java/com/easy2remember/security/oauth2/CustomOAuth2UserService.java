package com.easy2remember.security.oauth2;

import com.easy2remember.dao.impl.JdbcUserRepo;
import com.easy2remember.entity.impl.main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private JdbcUserRepo jdbcUserRepo;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth = new DefaultOAuth2UserService().loadUser(userRequest);

        if(oauth != null && oauth.getAttribute("sub") != null) {

//            System.out.println(userRequest);
//            System.out.println(oauth.getAttributes());
//            try {
//                User user = this.jdbcUserRepo.findByGoogleSubIdBySub(oauth.getAttribute("sub"))
//                        .orElseThrow(() -> new AccountExpiredException("No such user exists"));
//            } catch (EmptyResultDataAccessException e) {
//                throw new AccountExpiredException("No such user exists");
//            }

        }

        return oauth;
    }



    @Autowired
    public void setJdbcUserRepo(JdbcUserRepo jdbcUserRepo) {
        this.jdbcUserRepo = jdbcUserRepo;
    }
}
