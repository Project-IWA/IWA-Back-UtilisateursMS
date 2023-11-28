package com.iwa.utilisateurs.repository;

import com.iwa.utilisateurs.model.Formule;
import com.iwa.utilisateurs.model.TypeFormule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormuleRepository extends JpaRepository<Formule, Long> {

    Optional<Formule> findByTypeFormule(TypeFormule typeFormule);

}
