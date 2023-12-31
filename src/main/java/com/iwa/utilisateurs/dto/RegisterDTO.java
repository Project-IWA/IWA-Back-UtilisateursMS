package com.iwa.utilisateurs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class RegisterDTO {
    private String username;
    private String password;
    private String prenom;
    private String nom;
    private Long idFormule;
    private Date dateDebutSouscription;
    private Date dateFinSouscription;
}