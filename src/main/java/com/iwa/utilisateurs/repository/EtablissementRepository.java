package com.iwa.utilisateurs.repository;

import com.iwa.utilisateurs.model.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
}
