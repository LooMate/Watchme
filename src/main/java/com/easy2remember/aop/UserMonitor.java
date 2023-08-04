package com.easy2remember.aop;


import com.easy2remember.exceptions.users.UsernameDoesNotMatchLoggedUserException;
import com.easy2remember.security.CustomUserDetails;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Aspect
@Controller
public class UserMonitor {


    @Before("com.easy2remember.aop.PointcutContainer.userAuthentic(username)")
    public void beforeUserActionPointcut(String username) {
        if(!username.equals(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername())) {
            throw new UsernameDoesNotMatchLoggedUserException("Username does not match logged User");
        }
    }

}
