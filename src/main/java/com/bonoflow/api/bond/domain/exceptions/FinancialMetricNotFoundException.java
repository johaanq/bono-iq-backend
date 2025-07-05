package com.bonoflow.api.bond.domain.exceptions;

public class FinancialMetricNotFoundException extends RuntimeException {
    public FinancialMetricNotFoundException(Long financialMetricId) {
        super("Financial Metric with id " + financialMetricId + " not found");
    }
}