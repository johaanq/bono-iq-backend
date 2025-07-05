package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.UpdateBondCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateBondResource;

public class UpdateBondCommandFromResourceAssembler {

    public static UpdateBondCommand toCommandFromResource(UpdateBondResource resource, Long id) {
        return new UpdateBondCommand(
                id,
                resource.name(),
                resource.faceValue(),
                resource.interestRate(),
                resource.rateType(),
                resource.compounding(),
                resource.paymentFrequency(),
                resource.currency(),
                resource.graceType(),
                resource.gracePeriod(),
                resource.issueDate(),
                resource.maturityDate(),
                resource.issuanceExpenses(),
                resource.placementExpenses(),
                resource.structuringExpenses(),
                resource.cavaliExpenses(),
                resource.marketRate()
        );
    }
}