package com.iwa.utilisateurs.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data               // Generates getters, setters, toString, equals, and hashCode methods
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")  // This maps the field to the id_user column in the database
    private Long idUser;

    private String prenom;
    private String nom;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, message = "Password must have at least 6 characters.")
    private String password;
    private String role;
    private String tel;
    private String rue;

    @Column(name = "code_postal")
    private String codePostal;
    private String ville;

    @Column(name = "numero_siret")
    private String numeroSiret;

    @Column(name="doc_justificatif")
    private String docJustificatif;
    private String etat;

    @ManyToOne
    @JoinColumn(name = "id_formule")
    private Formule formule;

    @ManyToOne
    @JoinColumn(name = "id_etablissement")
    private Etablissement etablissement;

    // Dates for subscription should be of type LocalDate or Date.
    // I'm keeping it simple here for brevity.

    @Column(name = "date_debut_souscription")
    private String dateDebutSouscription;

    @Column(name = "date_fin_souscription")
    private String dateFinSouscription;

    // Getters, setters, and other necessary methods...
}
