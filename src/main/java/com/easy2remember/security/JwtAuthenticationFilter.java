package com.easy2remember.security;

import com.easy2remember.exceptions.jwt.JwtTokenWasExpiredException;
import com.easy2remember.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHttpRequestHeader(request);

        boolean shouldAuth = !"/login".equals(request.getRequestURI()) && !"/logout".equals(request.getRequestURI())
                && !"/api/user/register".equals(request.getRequestURI());

        if (token != null && shouldAuth) {
            UserDetails userDetails = null;

            try {
                this.jwtService.validateToken(token);
                userDetails = userDetailsService.loadUserByUsername(jwtService.getUsernameFromToken(token));
            } catch (JwtTokenWasExpiredException e) {
                response.sendError(401, "Token was expired");
                return;
            } catch (Exception e) {
                response.sendError(401, "Bad token");
                return;
            }

            var authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                    userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
        }


        filterChain.doFilter(request, response);
    }

    private String getTokenFromHttpRequestHeader(HttpServletRequest request) {
        String tokenHeader = request.getHeader("authorization");

        if (tokenHeader != null && !tokenHeader.isBlank()) {
            return tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : null;
        }
        return null;
    }


}
