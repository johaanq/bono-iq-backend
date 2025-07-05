package com.bonoflow.api.bond.domain.model.commands;

import java.time.LocalDate;

public record UpdateCashFlowCommand(
        Long id,
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
) {}