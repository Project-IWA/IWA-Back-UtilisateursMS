package com.iwa.utilisateurs.dto;

import com.iwa.utilisateurs.model.Role;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsDTO{
    private String username;
    private String password;

    private String role;


    public UserDetailsDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole(){
        return role;
    }
}
