package com.easy2remember.controller;


import com.easy2remember.dto.ResponseTokens;
import com.easy2remember.exceptions.jwt.BadJwtTokenException;
import com.easy2remember.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/auth")
public class AuthenticationController {

    private JwtService jwtService;
    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @Autowired
    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/authenticate")
    public String authenticateToken() {
        return "Token is Valid";
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/refresh_tokens")
    public ResponseTokens refreshToken(HttpServletRequest request) {
        String token = request.getHeader("refresh_token");
        if (token != null && this.jwtService.validateToken(token)) {
            return this.jwtService.getNewTokens(token);
        }
        throw new BadJwtTokenException("Bad Jwt token");
    }


//    @GetMapping("/login/oauth2/code/google")
//    public ResponseEntity<?> oauth2Callback(@AuthenticationPrincipal OAuth2User oauth2User) {
//        // Handle the user details returned from Google's callback
//        // You can access user details from the oauth2User parameter
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.LOCATION, "http://192.168.0.101:3000/oauth2/callback");
//
//        String script = String.format("sessionStorage.setItem('message', '%s');", "message");
//        headers.add("X-Script", script);
//
//
//        System.out.println(oauth2User);
//        return ResponseEntity.status(302).headers(headers).body("{hello:hello}"); // Redirect the user to the dashboard or any other page
//    }


}
