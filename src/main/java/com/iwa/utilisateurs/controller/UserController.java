package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.exception.EtablissementNotFoundException;
import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.exception.UserNotFoundException;
import com.iwa.utilisateurs.model.Etablissement;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @Valid @RequestBody UserEntity userEntity) {
        // Make sure to set the userEntity's ID from the path variable
        userEntity.setIdUser(id);
        return new ResponseEntity<>(userService.updateUser(userEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GESTION DES ÉTABLISSEMENTS D'UN UTILISATEUR

    @GetMapping("/{id}/etablissements")
    public ResponseEntity<Set<Etablissement>> getEtablissements(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getEtablissements(id), HttpStatus.OK);
    }

    // Ajouter un établissement à un utilisateur
    @PostMapping("/{userId}/etablissements/{etablissementId}")
    public ResponseEntity<Void> addEtablissementToUser(@PathVariable Long userId, @PathVariable Long etablissementId) {
        try{
            userService.addEtablissement(userId, etablissementId);
            // return status 201 (created)
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            // return status 404 (not found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EtablissementNotFoundException e) {
            // return status 404 (not found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // Supprimer un établissement d'un utilisateur
    @DeleteMapping("/{userId}/etablissements/{etablissementId}")
    public ResponseEntity<Void> removeEtablissementFromUser(@PathVariable Long userId, @PathVariable Long etablissementId) {
        userService.removeEtablissement(userId, etablissementId);
        // return https status deleted (204)
        return ResponseEntity.noContent().build();
    }

    // Définir l'établissement principal pour un utilisateur
    @PatchMapping("/{userId}/etablissements/principal/{etablissementId}")
    public ResponseEntity<Void> setEtablissementPrincipal(@PathVariable Long userId, @PathVariable Long etablissementId) {
        userService.setEtablissementPrincipal(userId, etablissementId);
        // return https status updated (200)
        return ResponseEntity.ok().build();
    }


}

