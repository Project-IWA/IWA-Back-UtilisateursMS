package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.model.Etablissement;
import com.iwa.utilisateurs.service.EtablissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etablissement")
public class EtablissementController {

    @Autowired
    private EtablissementService service;

    @GetMapping
    public List<Etablissement> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Etablissement getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Etablissement create(@RequestBody Etablissement etablissement) {
        return service.save(etablissement);
    }

    @PutMapping("/{id}")
    public Etablissement update(@PathVariable Long id, @RequestBody Etablissement etablissement) {
        // Ensure the ID is set to the path variable
        etablissement.setIdEtablissement(id);
        return service.save(etablissement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
