package com.bonoflow.api.bond.domain.model.commands;

import java.time.LocalDate;

public record CreateCashFlowCommand(
        Long bondId,
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