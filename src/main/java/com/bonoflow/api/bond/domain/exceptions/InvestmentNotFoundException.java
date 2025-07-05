package com.bonoflow.api.bond.domain.exceptions;

public class InvestmentNotFoundException extends RuntimeException {
    public InvestmentNotFoundException(Long investmentId) {
        super("Investment with id " + investmentId + " not found");
    }
}