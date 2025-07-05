package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.entities.Investment;
import com.bonoflow.api.bond.interfaces.rest.resources.InvestmentResource;

public class InvestmentResourceFromEntity {
    public static InvestmentResource toResource(Investment investment) {
        return new InvestmentResource(
                investment.getId(),
                investment.getInvestorId(),
                investment.getBondId(),
                investment.getAmount(),
                investment.getInvestmentDate()
        );
    }
}