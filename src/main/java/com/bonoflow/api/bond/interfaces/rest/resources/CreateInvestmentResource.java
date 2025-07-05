package com.bonoflow.api.bond.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateInvestmentResource(
        Long investorId,
        Long bondId,
        Double amount,
        LocalDate investmentDate
) {
}