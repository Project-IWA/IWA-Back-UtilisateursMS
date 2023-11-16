package com.iwa.utilisateurs.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.iwa.utilisateurs.model.Role;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.service.UserService;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;
//import java.security.KeyPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {

    @Autowired
    private UserService userService;

    private String key  = SecurityConstants.JWT_SECRET;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        // Retrieve the user using the UserService
        UserEntity userEntity = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String token = Jwts.builder()
                .setSubject(username)
                .claim("userId", userEntity.getIdUser()) // Add user ID as a claim
                .claim("roles", userEntity.getRoles().stream() // Add roles as a claim
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key.getBytes(StandardCharsets.UTF_8))
                .compact();

        System.out.println("New token :");
        System.out.println(token);
        return token;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }

}
