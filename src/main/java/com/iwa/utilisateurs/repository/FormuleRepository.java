package com.iwa.utilisateurs.repository;

import com.iwa.utilisateurs.model.Formule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleRepository extends JpaRepository<Formule, Long> {
}
