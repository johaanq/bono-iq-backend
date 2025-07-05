package com.bonoflow.api.profile.interfaces.rest.resources;

import com.bonoflow.api.bond.domain.model.valueobjects.BondCompounding;
import com.bonoflow.api.bond.domain.model.valueobjects.BondRateType;

public record UpdateConfigurationResource(
        String currency,
        String rateType,
        String compounding
) {}