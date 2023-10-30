package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.model.AvoirEtablissement;
import com.iwa.utilisateurs.service.AvoirEtablissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avoir_etablissements")
public class AvoirEtablissementController {

    @Autowired
    private AvoirEtablissementService service;

    @GetMapping
    public List<AvoirEtablissement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AvoirEtablissement getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public AvoirEtablissement create(@RequestBody AvoirEtablissement avoirEtablissement) {
        return service.save(avoirEtablissement);
    }

    @PutMapping("/{id}")
    public AvoirEtablissement update(@PathVariable Long id, @RequestBody AvoirEtablissement avoirEtablissement) {
        avoirEtablissement.setId(id);  // Ensure the ID is set to the path variable
        return service.save(avoirEtablissement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
