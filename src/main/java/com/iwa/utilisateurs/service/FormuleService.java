package com.iwa.utilisateurs.service;

import com.iwa.utilisateurs.exception.ResourceNotFoundException;
import com.iwa.utilisateurs.model.Formule;
import com.iwa.utilisateurs.repository.FormuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormuleService {

    @Autowired
    private FormuleRepository repository;

    public List<Formule> getAll() {
        return repository.findAll();
    }

    public Formule getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Formule with id " + id + " not found"));
    }

    public Formule save(Formule formule) {
        return repository.save(formule);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Formule with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
