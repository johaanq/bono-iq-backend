package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.commands.CreateFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;

import java.util.Optional;

public interface FinancialMetricCommandService {
    Long handle(CreateFinancialMetricCommand command);
    Optional<FinancialMetric> handle(UpdateFinancialMetricCommand command);
    void handle(DeleteFinancialMetricCommand command);
}