package com.iwa.utilisateurs.security;


// Importing necessary classes
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Annotating this class as a Component so it can be detected during classpath scanning

/**
 * When a user tries to access a protected resource without being authenticated,
 * or their authentication token is invalid (for example, if their JWT has expired),
 * the commence method is called. The method responds by setting the HTTP status code to 401 Unauthorized
 * and sends back the error message contained in the AuthenticationException.
 * This informs the client (such as a browser or a mobile app) that the request was not authorized,
 * and the user should be prompted to authenticate.
 * This class doesn't redirect users to a login page because it's intended for REST APIs where an HTTP status code
 * is the preferred way to handle authentication errors.
 * */

/***
 * @Component
 * public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
 *
 *     // The `commence` method is called whenever an `AuthenticationException` is thrown
 *     // This usually means the user is trying to access a protected resource without being authenticated
 *     @Override
 *     public void commence(HttpServletRequest request,
 *                          HttpServletResponse response,
 *                          AuthenticationException authException) throws IOException, ServletException {
 *         // Respond with a 401 Unauthorized error and the exception message
 *         // This is typically what you'd want to do if a user tries to access a secure REST resource without proper authentication
 *         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
 *     }
 * }
 * */

