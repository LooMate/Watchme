package com.easy2remember.security;

import com.easy2remember.dto.ResponseTokens;
import com.easy2remember.exceptions.jwt.BadJwtTokenException;
import com.easy2remember.exceptions.jwt.JwtTokenWasExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtService {

    @Value("${accessJwtExpiration}")
    private int accessJwtExpirationInMin;

    @Value("${refreshJwtExpiration}")
    private int refreshJwtExpirationInMin;

    @Value("${jwtSecret}")
    private String jwtSecret;

    private Key signatureKey;

    @PostConstruct
    public void init() {
        signatureKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public ResponseTokens getNewTokens(String refreshToken) {
        return new ResponseTokens(
                this.generateToken(new UsernamePasswordAuthenticationToken(
                        this.getUsernameFromToken(refreshToken), null, null)),

                this.generateRefreshToken(new UsernamePasswordAuthenticationToken(
                        this.getUsernameFromToken(refreshToken), null, null)));
    }

    public String generateToken(Authentication authentication) {
        return this.generateToken(authentication, this.accessJwtExpirationInMin);
    }

    public String generateRefreshToken(Authentication authentication) {
        return this.generateToken(authentication, this.refreshJwtExpirationInMin);
    }


    private String generateToken(Authentication authentication, int jwtExpirationInMin) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + (jwtExpirationInMin * 60L * 1000));

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(this.signatureKey)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.signatureKey).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.signatureKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtTokenWasExpiredException("JWT token was expired");
        } catch (Exception e) {
            throw new BadJwtTokenException("Bad jwt token");
        }
    }

}
