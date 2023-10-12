package com.iwa.utilisateurs.repository;

import com.iwa.utilisateurs.model.AvoirEtablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvoirEtablissementRepository extends JpaRepository<AvoirEtablissement, Long> {
    // Additional custom query methods can be defined here if needed.
}
