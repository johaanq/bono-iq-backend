package com.bonoflow.api.profile.domain.exceptions;

public class InvestorNotFoundException extends RuntimeException {
    public InvestorNotFoundException(Long id) {
        super("Investor with id " + id + " not found");
    }
}