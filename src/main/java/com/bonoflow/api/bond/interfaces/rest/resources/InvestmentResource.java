package com.bonoflow.api.bond.interfaces.rest.resources;

import java.time.LocalDate;

public record InvestmentResource(
        Long id,
        Long investorId,
        Long bondId,
        Double amount,
        LocalDate investmentDate
) {
}