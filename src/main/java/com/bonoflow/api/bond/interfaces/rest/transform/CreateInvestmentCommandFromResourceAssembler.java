package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.CreateInvestmentCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateInvestmentResource;

public class CreateInvestmentCommandFromResourceAssembler {

    public static CreateInvestmentCommand toCommandFromResource(CreateInvestmentResource resource) {
        return new CreateInvestmentCommand(
                resource.bondId(),
                resource.investorId(),
                resource.amount(),
                resource.investmentDate()
        );
    }
}