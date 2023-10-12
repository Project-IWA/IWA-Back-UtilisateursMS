package com.iwa.utilisateurs.service;

import com.iwa.utilisateurs.exception.ResourceNotFoundException;
import com.iwa.utilisateurs.model.Etablissement;
import com.iwa.utilisateurs.repository.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissementService {

    @Autowired
    private EtablissementRepository repository;

    public List<Etablissement> getAll() {
        return repository.findAll();
    }

    public Etablissement getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Etablissement with id " + id + " not found"));
    }

    public Etablissement save(Etablissement etablissement) {
        return repository.save(etablissement);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Etablissement with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
