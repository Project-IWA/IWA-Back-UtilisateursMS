package com.iwa.utilisateurs.controller;

import com.iwa.utilisateurs.dto.UserAuthDTO;
import com.iwa.utilisateurs.dto.UserDetailsDTO;
import com.iwa.utilisateurs.dto.UserUpdateDTO;
import com.iwa.utilisateurs.exception.EtablissementNotFoundException;
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

    @GetMapping("/by-customusername/{username}")
    public ResponseEntity<UserDetailsDTO> getUserByUserName(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(user -> new ResponseEntity<>(new UserDetailsDTO(user.getUsername(), user.getPassword(), user.getRole().name()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Only the user itself can update his own profile, and this is done by checking the id of the user in the header
    @PutMapping
    public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO,
                                                 @RequestHeader("AuthUserId") Long idUser) {
        // Check if the id of the user in the header is the same as the id of the user in the body
        if(idUser != userUpdateDTO.getIdUser()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        // Get the user by id
        UserEntity userEntity = userService.getUserById(idUser).orElseThrow(() -> new UserNotFoundException(idUser));
        userEntity.setFieldsFromUserUpdateDTO(userUpdateDTO);
        return new ResponseEntity<>(userService.updateUser(userEntity), HttpStatus.OK);
    }

    // Only the admin can delete a user and this is handled in the api-gateway
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GESTION DES ÉTABLISSEMENTS D'UN UTILISATEUR

    // Get all etablissements of the connected user (by the id of user in the header)
    @GetMapping("/etablissements/all")
    public ResponseEntity<Set<Etablissement>> getEtablissements(@RequestHeader("AuthUserId") Long idUser) {
        return new ResponseEntity<>(userService.getEtablissements(idUser), HttpStatus.OK);
    }

    // Ajouter un établissement à un utilisateur (by the id of user in the header)
    @PostMapping("/etablissements/{etablissementId}")
    public ResponseEntity<Void> addEtablissementToUser(@PathVariable Long etablissementId,
                                                       @RequestHeader("AuthUserId") Long idUser) {
        try{
            userService.addEtablissement(idUser, etablissementId);
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
    @DeleteMapping("/etablissements/{etablissementId}")
    public ResponseEntity<Void> removeEtablissementFromUser(@PathVariable Long etablissementId,
                                                            @RequestHeader("AuthUserId") Long idUser) {
        userService.removeEtablissement(idUser, etablissementId);
        // return https status deleted (204)
        return ResponseEntity.noContent().build();
    }

    // Définir l'établissement principal pour un utilisateur
    @PatchMapping("/etablissements/principal/{etablissementId}")
    public ResponseEntity<Void> setEtablissementPrincipal(@PathVariable Long etablissementId,
                                                          @RequestHeader("AuthUserId") Long idUser) {
        userService.setEtablissementPrincipal(idUser, etablissementId);
        // return https status updated (200)
        return ResponseEntity.ok().build();
    }


}

