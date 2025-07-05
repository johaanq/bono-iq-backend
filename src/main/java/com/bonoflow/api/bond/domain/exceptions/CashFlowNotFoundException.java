package com.bonoflow.api.bond.domain.exceptions;

public class CashFlowNotFoundException extends RuntimeException {
    public CashFlowNotFoundException(Long cashFlowId) {
        super("Cash Flow with id " + cashFlowId + " not found");
    }
}