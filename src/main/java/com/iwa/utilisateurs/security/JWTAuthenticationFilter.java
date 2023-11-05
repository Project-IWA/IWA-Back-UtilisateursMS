package com.iwa.utilisateurs.security;


// Importing required classes and packages
import com.iwa.utilisateurs.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** JWTAuthenticationFilter is used to intercept each request once and perform JWT validation
* This filter extends OncePerRequestFilter to ensure it is applied once per request.
* Its purpose is to intercept the HTTP request, check for the presence of a JWT in the Authorization header,
* validate the token, and then set the Authentication in the SecurityContext.
* The SecurityContext is used by Spring Security to determine the identity and roles of the current user.
* If the token is valid, the filter creates a UsernamePasswordAuthenticationToken with the user's details and authorities,
* effectively authenticating the user for the duration of the request.
* The filter then calls filterChain.doFilter to pass control to the next filter in the chain,
* which could be another security filter or eventually the servlet that handles the request if no more filters are configured.
* */

// Annotating this class as a Component so it can be detected during classpath scanning

/**
 * @Component
 * public class JWTAuthenticationFilter extends OncePerRequestFilter {
 *
 *     // Injecting the JWTGenerator and CustomUserDetailsService beans
 *     @Autowired
 *     private JWTGenerator tokenGenerator;
 *
 *     @Autowired
 *     private CustomUserDetailsService customUserDetailsService;
 *
 *     // The main method that intercepts each request and processes it
 *     @Override
 *     protected void doFilterInternal(HttpServletRequest request,
 *                                     HttpServletResponse response,
 *                                     FilterChain filterChain) throws ServletException, IOException {
 *         // Attempt to extract a JWT token from the request
 *         String token = getJWTFromRequest(request);
 *         System.out.println("token: " + token);
 *
 *         // Check if the token is present and valid
 *         if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
 *             // Extract the username from the valid token
 *             String username = tokenGenerator.getUsernameFromJWT(token);
 *
 *             // Load the user details associated with that username
 *             UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
 *             // Create an Authentication object using the UserDetails
 *             UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
 *                     userDetails.getAuthorities());
 *             // Set the authentication details from the request
 *             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 *             // Set the Authentication in the SecurityContext
 *             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
 *         }
 *         // Continue the filter chain with the modified or unmodified request and response objects
 *         filterChain.doFilter(request, response);
 *     }
 *
 *     // Helper method to extract the JWT from the Authorization header of the request
 *     private String getJWTFromRequest(HttpServletRequest request) {
 *         // Retrieve the Authorization header from the request
 *         String bearerToken = request.getHeader("Authorization");
 *         // Check if the header is present and starts with "Bearer "
 *         if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
 *             // Extract the JWT token by removing "Bearer " prefix
 *             return bearerToken.substring(7, bearerToken.length());
 *         }
 *         // Return null if no JWT found in the request header
 *         return null;
 *     }
 * }
 * **/


