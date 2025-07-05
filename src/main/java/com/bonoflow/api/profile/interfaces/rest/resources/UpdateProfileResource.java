package com.bonoflow.api.profile.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateProfileResource(
        String firstName,
        String lastName,
        LocalDate birthDate,
        String description,
        String photo,
        String company,
        String ruc
) {
}