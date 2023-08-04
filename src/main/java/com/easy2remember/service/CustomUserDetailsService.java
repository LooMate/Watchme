package com.easy2remember.service;


import com.easy2remember.dao.impl.JdbcUserRepo;
import com.easy2remember.entity.impl.main.Role;
import com.easy2remember.entity.impl.main.User;
import com.easy2remember.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private JdbcUserRepo jdbcUserRepo;

     @Autowired
    public CustomUserDetailsService(JdbcUserRepo jdbcUserRepo) {
        this.jdbcUserRepo = jdbcUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
         try {
             user = this.jdbcUserRepo.findByUsername(username)
                     .orElseThrow(() -> new UsernameNotFoundException("Username was NOT found"));
         } catch (EmptyResultDataAccessException e) {
             throw new UsernameNotFoundException("Username was NOT found");
         }

        Role[] roles = this.jdbcUserRepo.findRolesForUserByUserId(user.getId());
        return new CustomUserDetails(username, user.getId(), user.getPassword(), Arrays.asList(roles),
                user.isEnabled());
    }

}
