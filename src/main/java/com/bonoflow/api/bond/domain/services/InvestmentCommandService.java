// src/main/java/com/bonoflow/api/bond/domain/services/InvestmentCommandService.java
package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.commands.CreateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteInvestmentCommand;
import com.bonoflow.api.bond.domain.model.entities.Investment;

import java.util.Optional;

public interface InvestmentCommandService {
    Long handle(CreateInvestmentCommand command);
    Optional<Investment> handle(UpdateInvestmentCommand command);
    void handle(DeleteInvestmentCommand command);
}