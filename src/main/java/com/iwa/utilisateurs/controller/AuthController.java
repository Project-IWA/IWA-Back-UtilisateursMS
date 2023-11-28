package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.dto.AuthResponseDTO;
import com.iwa.utilisateurs.dto.LoginDTO;
import com.iwa.utilisateurs.dto.RegisterDTO;
import com.iwa.utilisateurs.dto.UserAuthDTO;
import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.model.Formule;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.security.JWTGenerator;
import com.iwa.utilisateurs.service.FormuleService;
import com.iwa.utilisateurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;


    @GetMapping
    public ResponseEntity<UserEntity> getAuthUserByToken(@RequestHeader("AuthUserId") Long userId) {
        System.out.println("user id sent by token in get auth : " + userId);
        Optional<UserEntity> userEntity = userService.getUserById(userId);
        return userEntity
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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
            System.out.println("registerDTO: " + registerDTO);
            // call the service to create the user
            UserEntity registeredUserEntity = userService.createUser(registerDTO);
            System.out.println("registeredUserEntity: " + registeredUserEntity);
            return new ResponseEntity<>(registeredUserEntity, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return new ResponseEntity<>("An error occurred while registering the userEntity.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        // Set the context and generate the token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        // get the userEntity and give the dto to the response
        UserEntity userEntity = userService.getUserByUsername(loginDto.getUsername()).get();
        UserAuthDTO userAuthDTO = UserAuthDTO.mapFromUserEntity(userEntity);
        return new ResponseEntity<>(new AuthResponseDTO(token,userAuthDTO), HttpStatus.OK);
    }



}

