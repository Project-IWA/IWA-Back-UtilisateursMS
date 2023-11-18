package com.iwa.utilisateurs.service;

import com.iwa.utilisateurs.dto.RegisterDTO;
import com.iwa.utilisateurs.exception.EtablissementNotFoundException;
import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.exception.UserNotFoundException;
import com.iwa.utilisateurs.model.Etablissement;
import com.iwa.utilisateurs.model.Role;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.repository.EtablissementRepository;
import com.iwa.utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EtablissementRepository etablissementRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity createUser(RegisterDTO registerDTO) {
        // create a user and map the regiterDTO to the userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setPassword(registerDTO.getPassword());

        System.out.println("userEntity: " + userEntity);

        // check for existing user
        if(userRepository.existsByUsername(userEntity.getUsername())) {
            throw new UserAlreadyExistsException(userEntity.getUsername());
        }

        // encrypt the userEntity's password then save the userEntity
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        // set the role with the value "Recruteur" of the enum Role by default
        userEntity.setRole(Role.Recruteur);
        
        System.out.println("userEntity.role: " + userEntity.getRole());
        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) {
        if(!userRepository.existsById(userEntity.getIdUser())) {
            throw new UserNotFoundException(userEntity.getIdUser());
        }
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        // Check if user exists
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    // Gestion des établissements du user

    // Get all etablissements of a user
    public Set<Etablissement> getEtablissements(Long userId) {
        // Check if user exists
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getEtablissements();
    }

    // Add an etablissement to a user
    @Transactional
    public void addEtablissement(Long userId, Long etablissementId) {
        // Check if user exists
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Check if etablissement exists
        Etablissement etablissement = etablissementRepository.findById(etablissementId)
                .orElseThrow(() -> new EtablissementNotFoundException(etablissementId));

        // add the etablissement to the user
        user.getEtablissements().add(etablissement);
        userRepository.save(user);
    }

    // Remove an etablissement from a user
    @Transactional
    public void removeEtablissement(Long userId, Long etablissementId) {

        // Check if user exists
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Check if etablissement exists
        Etablissement etablissement = etablissementRepository.findById(etablissementId)
                .orElseThrow(() -> new EtablissementNotFoundException(etablissementId));

        // Remove the etablissement from the user
        user.getEtablissements().remove(etablissement);

        // Si l'établissement à supprimer est l'établissement principal, le réinitialiser
        if (user.getEtablissementPrincipal() != null &&
                user.getEtablissementPrincipal().getIdEtablissement().equals(etablissementId)) {
            user.setEtablissementPrincipal(null);
        }
        userRepository.save(user);
    }

    // Set the principal etablissement of a user
    @Transactional
    public void setEtablissementPrincipal(Long userId, Long etablissementId) {
        // Check if user exists
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Check if etablissement exists
        Etablissement etablissement = etablissementRepository.findById(etablissementId)
                .orElseThrow(() -> new EtablissementNotFoundException(etablissementId));

        // Set the etablissement as the principal etablissement of the user
        user.setEtablissementPrincipal(etablissement);
        userRepository.save(user);
    }

}
