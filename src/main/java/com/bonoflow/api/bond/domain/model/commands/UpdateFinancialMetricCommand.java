package com.bonoflow.api.bond.domain.model.commands;

import java.time.LocalDate;

public record UpdateFinancialMetricCommand(
        Long id,
        Double tcea,
        Double trea,
        Double duration,
        Double modifiedDuration,
        Double convexity,
        Double marketPrice,
        LocalDate calculationDate
) {
}