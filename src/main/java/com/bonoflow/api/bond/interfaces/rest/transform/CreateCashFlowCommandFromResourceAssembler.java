package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.CreateCashFlowCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateCashFlowResource;

public class CreateCashFlowCommandFromResourceAssembler {

    public static CreateCashFlowCommand toCommandFromResource(CreateCashFlowResource resource) {
        return new CreateCashFlowCommand(
                resource.bondId(),
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