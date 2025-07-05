package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import com.bonoflow.api.bond.interfaces.rest.resources.CashFlowResource;

public class CashFlowResourceFromEntity {
    public static CashFlowResource toResource(CashFlow cashFlow) {
        return new CashFlowResource(
                cashFlow.getId(),
                cashFlow.getBondId(),
                cashFlow.getPeriod(),
                cashFlow.getDate(),
                cashFlow.getInitialBalance(),
                cashFlow.getInterest(),
                cashFlow.getAmortization(),
                cashFlow.getInstallment(),
                cashFlow.getFinalBalance(),
                cashFlow.getFixedInstallment(),
                cashFlow.getExpenses(),
                cashFlow.getInvestorFlow()
        );
    }
}