package com.iwa.utilisateurs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwa.utilisateurs.dto.AuthResponseDTO;
import com.iwa.utilisateurs.dto.LoginDTO;
import com.iwa.utilisateurs.dto.RegisterDTO;
import com.iwa.utilisateurs.dto.UserAuthDTO;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.security.JWTGenerator;
import com.iwa.utilisateurs.service.CustomUserDetailsService;
import com.iwa.utilisateurs.service.UserService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthController authController;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTGenerator jwtGenerator;

    @Test
    public void testLogin() throws Exception {
        // given
        String username = "testUser";
        String password = "password";
        String token = "fakeToken";
        LoginDTO loginDTO = new LoginDTO(username, password);
        Authentication authentication = mock(Authentication.class);
        UserEntity userEntity = new UserEntity(); // Populate this as needed
        userEntity.setIdUser(1L);
        userEntity.setUsername(username);
        UserAuthDTO userAuthDTO = UserAuthDTO.mapFromUserEntity(userEntity);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication)).thenReturn(token);
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(userEntity));
        when(authController.login(loginDTO)).thenReturn(new ResponseEntity<>(new AuthResponseDTO(token,userAuthDTO), HttpStatus.OK));

        // when + then
        this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(token))
                .andExpect(jsonPath("$.user.idUser").exists())
                .andExpect(jsonPath("$.user.username").exists());
    }

    @Test
    public void testRegister() throws Exception {
        // Given
        String username = "testUser";
        String password = "testPass";
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(username);
        registerDTO.setPassword(password);
        // ... set other properties on registerDTO as needed ...

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        // ... set properties on userEntity as needed ...

        when(userService.createUser(any(RegisterDTO.class))).thenReturn(userEntity);
        when(authController.register(any(RegisterDTO.class)))
                .thenReturn(new ResponseEntity(userEntity, HttpStatus.CREATED));


        // When + Then
        this.mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"));
        // ... other assertions as needed ...
    }

    // Helper method to convert object to JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Add more tests for other endpoints
}
