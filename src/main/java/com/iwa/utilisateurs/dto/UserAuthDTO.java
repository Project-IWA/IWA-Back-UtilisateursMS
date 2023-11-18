package com.iwa.utilisateurs.dto;

import com.iwa.utilisateurs.model.Etablissement;
import com.iwa.utilisateurs.model.Formule;
import com.iwa.utilisateurs.model.Role;
import com.iwa.utilisateurs.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// This DTO is a copie of the UserEntity Model but without the password field
@Data               // Generates getters, setters, toString, equals, and hashCode methods
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class UserAuthDTO {

    private Long idUser;
    private String prenom;
    private String nom;
    private String username;
    private String email;
    private Role role; // ADMIN, BASIC
    private boolean enabled = false;
    private String tel;
    private String numRue;
    private String rue;
    private String codePostal;
    private String ville;
    private String pays;
    private String numeroSiret;
    private String docJustificatif;
    private String etat;
    private Formule formule;
    private Etablissement etablissementPrincipal;
    private Set<Etablissement> etablissements = new HashSet<>();
    private Date dateDebutSouscription;
    private Date dateFinSouscription;

    public static UserAuthDTO mapFromUserEntity(UserEntity userEntity){
        return new UserAuthDTO(
                userEntity.getIdUser(),
                userEntity.getPrenom(),
                userEntity.getNom(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.isEnabled(),
                userEntity.getTel(),
                userEntity.getNumRue(),
                userEntity.getRue(),
                userEntity.getCodePostal(),
                userEntity.getVille(),
                userEntity.getPays(),
                userEntity.getNumeroSiret(),
                userEntity.getDocJustificatif(),
                userEntity.getEtat(),
                userEntity.getFormule(),
                userEntity.getEtablissementPrincipal(),
                userEntity.getEtablissements(),
                userEntity.getDateDebutSouscription(),
                userEntity.getDateFinSouscription()
        );

    }
}
