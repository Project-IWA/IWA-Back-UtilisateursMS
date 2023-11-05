package com.iwa.utilisateurs.exception;

public class EtablissementNotFoundException extends RuntimeException{

    public EtablissementNotFoundException(Long id) {
        super("Etablissement not found with id: " + id);
    }
}
