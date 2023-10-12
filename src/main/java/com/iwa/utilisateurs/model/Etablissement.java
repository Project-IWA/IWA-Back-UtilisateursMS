package com.iwa.utilisateurs.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etablissement")
@Data               // Generates getters, setters, toString, equals, and hashCode methods
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etablissement")  // This maps the field to the id_etablissement column in the database
    private Long idEtablissement;

    private String nom;

    // Getters, setters, and other necessary methods...
}
