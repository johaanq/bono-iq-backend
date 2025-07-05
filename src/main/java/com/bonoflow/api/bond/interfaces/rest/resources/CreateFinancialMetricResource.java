package com.bonoflow.api.bond.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateFinancialMetricResource(
        Long bondId,
        Double tcea,
        Double trea,
        Double duration,
        Double modifiedDuration,
        Double convexity,
        Double marketPrice,
        LocalDate calculationDate
) {
}