package com.iwa.utilisateurs.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private UserAuthDTO user;

    public AuthResponseDTO(String accessToken, UserAuthDTO user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}