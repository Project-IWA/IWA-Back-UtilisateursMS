package com.iwa.utilisateurs.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("UserEntity not found with id: " + id);
    }
}