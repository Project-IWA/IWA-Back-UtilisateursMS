package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.model.Formule;
import com.iwa.utilisateurs.service.FormuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formules")
public class FormuleController {

    @Autowired
    private FormuleService service;

    @GetMapping
    public List<Formule> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Formule getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Formule create(@RequestBody Formule formule) {
        return service.save(formule);
    }

    @PutMapping("/{id}")
    public Formule update(@PathVariable Long id, @RequestBody Formule formule) {
        // Ensure the ID is set to the path variable
        formule.setIdFormule(id);
        return service.save(formule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
