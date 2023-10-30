package com.iwa.utilisateurs.service;

import com.iwa.utilisateurs.dto.RegisterDTO;
import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.exception.UserNotFoundException;
import com.iwa.utilisateurs.model.Role;
import com.iwa.utilisateurs.model.UserEntity;
import com.iwa.utilisateurs.repository.RoleRepository;
import com.iwa.utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity createUser(RegisterDTO registerDTO) {
        // create a user and map the regiterDTO to the userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setPassword(registerDTO.getPassword());

        // check for existing user
        if(userRepository.existsByUsername(userEntity.getUsername())) {
            throw new UserAlreadyExistsException(userEntity.getUsername());
        }

        // encrypt the userEntity's password then save the userEntity
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        // set the role ("USER") by default

        Role roles = roleRepository.findByName("USER").get();
        userEntity.setRoles(Collections.singletonList(roles));

        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) {
        if(!userRepository.existsById(userEntity.getIdUser())) {
            throw new UserNotFoundException(userEntity.getIdUser());
        }
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
