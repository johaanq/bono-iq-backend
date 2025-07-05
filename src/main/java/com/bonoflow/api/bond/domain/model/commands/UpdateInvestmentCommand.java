package com.bonoflow.api.bond.domain.model.commands;

import java.time.LocalDate;

public record UpdateInvestmentCommand(Long id,
                                      Double amount,
                                      LocalDate investmentDate) {
}
