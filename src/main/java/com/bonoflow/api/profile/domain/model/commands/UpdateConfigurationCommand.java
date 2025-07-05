package com.bonoflow.api.profile.domain.model.commands;

public record UpdateConfigurationCommand(
        Long id,
        String currency,
        String rateType,
        String compounding // Puede ser null si la tasa es efectiva
) {
}
