package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.CreateFinancialMetricCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateFinancialMetricResource;

public class CreateFinancialMetricCommandFromResourceAssembler {
    public static CreateFinancialMetricCommand toCommand(CreateFinancialMetricResource resource) {
        return new CreateFinancialMetricCommand(
                resource.bondId(),
                resource.tcea(),
                resource.trea(),
                resource.duration(),
                resource.modifiedDuration(),
                resource.convexity(),
                resource.marketPrice(),
                resource.calculationDate()
        );
    }
}