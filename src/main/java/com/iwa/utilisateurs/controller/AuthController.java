package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.model.User;
import com.iwa.utilisateurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    // First, we use the @Valid annotation to make sure that the request body is valid.
    // Then, we use the @RequestBody annotation to map the request body to the User object.
    // Finally, we use the @PostMapping annotation to map HTTP POST requests to the /api/auth/signup endpoint.
    /* In the method, we try to create a user, and based on the result,
    we send an appropriate HTTP response.
    If the user registration is successful, we return the created User
    object with a 201 Created status.
    If the user already exists, we return a 409 Conflict status,
    and for any other errors, we return a 500 Internal Server Error status.
    * */
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        try {
            User registeredUser = userService.createUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while registering the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

