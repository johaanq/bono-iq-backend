package com.bonoflow.api.profile.domain.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Advisor with id " + id + " not found");
    }
}