package com.easy2remember.security;


import com.easy2remember.security.oauth2.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private DataSource dataSource;
    private final Environment environment;

    private String[] cors;

    @Autowired
    public SecurityConfig(DataSource dataSource, Environment environment) {
        this.environment = environment;
        this.dataSource = dataSource;
        cors = environment.getProperty("cors.frontend").split(",");
    }


    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
        roleHierarchyImpl.setHierarchy("ADMIN > MODER > USER");
        return roleHierarchyImpl;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/register", "/api/post/more", "/api/post/overview",
                                "/api/auth/refresh_tokens", "/api/post/daily", "/api/post/{username}/overview", "/login/oauth2/code/*").permitAll()
                        .requestMatchers("/api/collect/get/*", "/api/collect/update/*").hasAuthority("ADMIN")
                        .requestMatchers("/api/collect/create/*", "/api/collect/save/*", "/api/analytics/*",
                                "/api/user/*").hasAuthority("USER")
                        .requestMatchers("/api/user/show/*", "/api/user/{publisherName}/subscribers").permitAll()
                        .requestMatchers("/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login.successHandler(this.customAuthenticationSuccessHandler()))
                .oauth2Login(auth -> auth.userInfoEndpoint(uie -> uie.userService(this.oAuth2UserService()))
                        .redirectionEndpoint(redirect -> redirect.baseUri("/login/oauth2/code/")))
                .logout(Customizer.withDefaults());
        http.addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public OAuth2UserService oAuth2UserService() {
        return new CustomOAuth2UserService();
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(cors);
            }
        };
    }



    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(this.jwtService());
    }

}
