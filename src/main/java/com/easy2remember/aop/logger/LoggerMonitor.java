package com.easy2remember.aop.logger;

import com.easy2remember.controller.*;
import com.easy2remember.security.CustomAuthenticationSuccessHandler;
import com.easy2remember.security.JwtAuthenticationFilter;
import com.easy2remember.security.JwtService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Aspect
@Controller
public class LoggerMonitor {

    private Logger usersControllerLogger = LoggerFactory.getLogger(UserController.class);
    private Logger postsControllerLogger = LoggerFactory.getLogger(PostsController.class);
    private Logger authenticationControllerLogger = LoggerFactory.getLogger(AuthenticationController.class);
    private Logger postsAnalyticsControllerLogger = LoggerFactory.getLogger(PostsAnalyticsController.class);
    private Logger userAnalyticsControllerLogger = LoggerFactory.getLogger(UserAnalyticsController.class);


    private Logger jwtServiceLogger = LoggerFactory.getLogger(JwtService.class);
    private Logger jwtAuthenticationFilterLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private Logger customAuthenticationSuccessHandlerLogger =
            LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);


//-=-=-=-=-=-==-= CONTROLLER LAYER -=-=-=-=-=-=-=-=-=-=-=


    public String getMessage(JoinPoint jp, boolean withArgs) {
        var requestContext = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        String path = requestContext != null ? requestContext.getRequest().getRequestURI() : "";
        String args = ".";

        StringBuilder methodName = new StringBuilder();
        methodName.append(jp.getSignature().getName());

        if (withArgs) {
            args = jp.getArgs() != null && jp.getArgs().length != 0
                    ? " with parameters " + Arrays.toString(jp.getArgs())
                    : ".";
        }

        this.getMethodSignature(((MethodSignature) jp.getSignature()).getMethod().getParameterTypes(), methodName);

        return jp.getSignature().getDeclaringType().getSimpleName() + " " + methodName + " " +
                path + " endpoint was triggered" + args;
    }

    public String getMessageWithoutArgs(JoinPoint jp) {
        return this.getMessage(jp, false);
    }

    public String getMessageWithArgs(JoinPoint jp) {
        return this.getMessage(jp, true);
    }

    private void getMethodSignature(Class<?>[] parameterTypes, StringBuilder sb) {
        sb.append("(");
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            sb.append(parameterType.getSimpleName());
            if (i < parameterTypes.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
    }

    @Before("com.easy2remember.aop.PointcutContainer.usersControllerMethods()")
    public void beforeUserControllerMethods(JoinPoint jp) {
        this.usersControllerLogger.trace(this.getMessageWithArgs(jp));
    }

    @Before("com.easy2remember.aop.PointcutContainer.postsControllerMethods()")
    public void beforePostsControllerMethods(JoinPoint jp) {
        this.postsControllerLogger.trace(this.getMessageWithArgs(jp));
    }

    @Before("com.easy2remember.aop.PointcutContainer.authenticationControllerMethods()")
    public void beforeAuthenticationControllerMethods(JoinPoint jp) {
        this.authenticationControllerLogger.trace(this.getMessageWithArgs(jp));
    }

    @Before("com.easy2remember.aop.PointcutContainer.userAnalyticsControllerMethods()")
    public void beforeUserAnalyticsControllerMethods(JoinPoint jp) {
        this.userAnalyticsControllerLogger.trace(this.getMessageWithArgs(jp));
    }

    @Before("com.easy2remember.aop.PointcutContainer.postsAnalyticsControllerMethods()")
    public void beforePostsAnalyticsControllerMethods(JoinPoint jp) {
        this.postsAnalyticsControllerLogger.trace(this.getMessageWithArgs(jp));
    }
//-=-=-=-=-=-==-= SECURITY LAYER -=-=-=-=-=-=-=-=-=-=-=

    @Before("com.easy2remember.aop.PointcutContainer.jwtServiceMethods()")
    public void beforeJwtService(JoinPoint jp) {
        this.jwtServiceLogger.trace(this.getMessageWithoutArgs(jp));
    }

    @Before("com.easy2remember.aop.PointcutContainer.jwtAuthenticationFilterMethods()")
    public void beforeJwtAuthenticationFilter(JoinPoint jp) {
        this.jwtAuthenticationFilterLogger.trace(this.getMessageWithoutArgs(jp));
    }

    @Before("com.easy2remember.aop.PointcutContainer.customAuthenticationSuccessHandlerMethods()")
    public void beforeCustomAuthenticationSuccessHandler(JoinPoint jp) {
        this.customAuthenticationSuccessHandlerLogger.trace(this.getMessageWithoutArgs(jp));
    }


}
