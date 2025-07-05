package com.bonoflow.api.profile.domain.model.commands;

import java.time.LocalDate;

public record UpdateProfileCommand(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String description,
        String photo,
        String company,
        String ruc
) {
}