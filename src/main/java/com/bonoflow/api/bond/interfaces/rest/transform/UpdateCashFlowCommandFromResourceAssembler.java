package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.UpdateCashFlowCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateCashFlowResource;

public class UpdateCashFlowCommandFromResourceAssembler {

    public static UpdateCashFlowCommand toCommandFromResource(UpdateCashFlowResource resource, Long id) {
        return new UpdateCashFlowCommand(
                id,
                resource.period(),
                resource.date(),
                resource.initialBalance(),
                resource.interest(),
                resource.amortization(),
                resource.installment(),
                resource.finalBalance(),
                resource.fixedInstallment(),
                resource.expenses(),
                resource.investorFlow()
        );
    }
}