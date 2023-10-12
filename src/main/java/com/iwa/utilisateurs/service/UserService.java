package com.iwa.utilisateurs.service;

import com.iwa.utilisateurs.exception.UserAlreadyExistsException;
import com.iwa.utilisateurs.exception.UserNotFoundException;
import com.iwa.utilisateurs.model.User;
import com.iwa.utilisateurs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if(!userRepository.existsById(user.getIdUser())) {
            throw new UserNotFoundException(user.getIdUser());
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}