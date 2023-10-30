package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.dto.AuthResponseDTO;
import com.iwa.utilisateurs.dto.LoginDTO;
import com.iwa.utilisateurs.dto.RegisterDTO;
import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.security.JWTGenerator;
import com.iwa.utilisateurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;

    @PostMapping("/register")
    // First, we use the @Valid annotation to make sure that the request body is valid.
    // Then, we use the @RequestBody annotation to map the request body to the UserEntity object.
    // Finally, we use the @PostMapping annotation to map HTTP POST requests to the /api/auth/signup endpoint.
    /* In the method, we try to create a userEntity, and based on the result,
    we send an appropriate HTTP response.
    If the userEntity registration is successful, we return the created UserEntity
    object with a 201 Created status.
    If the userEntity already exists, we return a 409 Conflict status,
    and for any other errors, we return a 500 Internal Server Error status.
    * */
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            // call the service to create the user
            UserEntity registeredUserEntity = userService.createUser(registerDTO);
            return new ResponseEntity<>(registeredUserEntity, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while registering the userEntity.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }



}

