package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.commands.CreateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteCashFlowCommand;
import com.bonoflow.api.bond.domain.model.entities.CashFlow;

import java.util.Optional;

public interface CashFlowCommandService {
    Long handle(CreateCashFlowCommand command);
    Optional<CashFlow> handle(UpdateCashFlowCommand command);
    void handle(DeleteCashFlowCommand command);
}