package com.iwa.utilisateurs.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avoir_etablissement")
@Data               // Generates getters, setters, toString, equals, and hashCode methods
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class AvoirEtablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-generated primary key for this relation table

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_etablissement")
    private Etablissement etablissement;

    // Getters, setters, and other necessary methods...
}
