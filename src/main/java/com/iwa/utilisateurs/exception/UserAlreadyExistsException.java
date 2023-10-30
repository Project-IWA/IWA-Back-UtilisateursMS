package com.iwa.utilisateurs.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String credential) {
        super("UserEntity already exists " + credential);
    }
}