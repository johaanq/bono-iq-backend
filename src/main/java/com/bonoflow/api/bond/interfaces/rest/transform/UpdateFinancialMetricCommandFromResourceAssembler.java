package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.UpdateFinancialMetricCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateFinancialMetricResource;

public class UpdateFinancialMetricCommandFromResourceAssembler {
    public static UpdateFinancialMetricCommand toCommand(UpdateFinancialMetricResource resource, Long id) {
        return new UpdateFinancialMetricCommand(
                id,
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