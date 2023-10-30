package com.iwa.utilisateurs.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data               // Generates getters, setters, toString, equals, and hashCode methods
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")  // This maps the field to the id_user column in the database
    private Long idUser;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @NotBlank(message = "username cannot be blank.")
    @Column(name = "username")
    private String username;

    //@NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, message = "Password must have at least 6 characters.")
    @Column(name = "password")
    private String password;

    // The join table user_roles that contains the id_user and its corresponding
    // roles with id_role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role"))
    private List<Role> roles = new ArrayList<>();

    // enabled : account is enabled or disabled
    @Column(name = "enabled")
    private boolean enabled = false;

    @Column(name = "tel")
    private String tel;

    @Column(name = "rue")
    private String rue;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "numero_siret")
    private String numeroSiret;

    @Column(name="doc_justificatif")
    private String docJustificatif;

    @Column(name = "etat")
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
