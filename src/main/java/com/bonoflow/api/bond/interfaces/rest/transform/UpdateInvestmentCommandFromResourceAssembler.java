package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.commands.UpdateInvestmentCommand;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateInvestmentResource;

public class UpdateInvestmentCommandFromResourceAssembler {

    public static UpdateInvestmentCommand toCommandFromResource(UpdateInvestmentResource resource, Long id) {
        return new UpdateInvestmentCommand(
                id,
                resource.amount(),
                resource.investmentDate()
        );
    }
}