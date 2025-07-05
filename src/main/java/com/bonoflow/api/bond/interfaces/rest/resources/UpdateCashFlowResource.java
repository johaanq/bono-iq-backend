package com.bonoflow.api.bond.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateCashFlowResource(
        Integer period,
        LocalDate date,
        Double initialBalance,
        Double interest,
        Double amortization,
        Double installment,
        Double finalBalance,
        Double fixedInstallment,
        Double expenses,
        Double investorFlow
) {
}