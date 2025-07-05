package com.bonoflow.api.bond.domain.model.commands;

import java.time.LocalDate;

public record CreateInvestmentCommand(Long bondId,
                                      Long investorId,
                                      Double amount,
                                      LocalDate investmentDate) {
}
