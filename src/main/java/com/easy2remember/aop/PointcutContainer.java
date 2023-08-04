package com.easy2remember.aop;


import org.aspectj.lang.annotation.Pointcut;

public class PointcutContainer {

    //check whether logged-in user matches given username
    @Pointcut("execution(public * com.easy2remember.controller.UserController.*(..)) &&" +
            "!execution(public * com.easy2remember.controller.UserController.showUser(..)) &&" +
            "args(username)")
    public void userAuthentic(String username) {
    }


    @Pointcut("execution(public * com.easy2remember.controller.UserController.*(..))")
    public void usersControllerMethods() {}

    @Pointcut("execution(public * com.easy2remember.controller.PostsController.*(..))")
    public void postsControllerMethods() {}

    @Pointcut("execution(public * com.easy2remember.controller.AuthenticationController.*(..))")
    public void authenticationControllerMethods() {}

    @Pointcut("execution(public * com.easy2remember.controller.UserAnalyticsController.*(..))")
    public void userAnalyticsControllerMethods() {}

    @Pointcut("execution(public * com.easy2remember.controller.PostsAnalyticsController.*(..))")
    public void postsAnalyticsControllerMethods() {}


    @Pointcut("execution(public * com.easy2remember.security.JwtService.*(..))")
    public void jwtServiceMethods(){}

    @Pointcut("execution(public * com.easy2remember.security.JwtAuthenticationFilter.*(..))")
    public void jwtAuthenticationFilterMethods(){}

    @Pointcut("execution(public * com.easy2remember.security.CustomAuthenticationSuccessHandler.*(..))")
    public void customAuthenticationSuccessHandlerMethods(){}

}
