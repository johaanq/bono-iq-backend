package com.bonoflow.api.bond.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateInvestmentResource(
        Double amount,
        LocalDate investmentDate
) {
}