package com.iwa.utilisateurs.service;


import com.iwa.utilisateurs.exception.ResourceNotFoundException;
import com.iwa.utilisateurs.model.AvoirEtablissement;
import com.iwa.utilisateurs.repository.AvoirEtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvoirEtablissementService {

    @Autowired
    private AvoirEtablissementRepository repository;

    public List<AvoirEtablissement> getAll() {
        return repository.findAll();
    }

    public AvoirEtablissement getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("AvoirEtablissement with id " + id + " not found"));
    }

    public AvoirEtablissement save(AvoirEtablissement avoirEtablissement) {
        return repository.save(avoirEtablissement);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("AvoirEtablissement with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    // Add any other necessary methods or business logic as needed
}




