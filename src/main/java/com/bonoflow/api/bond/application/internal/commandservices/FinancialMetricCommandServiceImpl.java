package com.bonoflow.api.bond.application.internal.commandservices;

import com.bonoflow.api.bond.domain.exceptions.BondNotFoundException;
import com.bonoflow.api.bond.domain.exceptions.FinancialMetricNotFoundException;
import com.bonoflow.api.bond.domain.model.commands.CreateFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import com.bonoflow.api.bond.domain.services.FinancialMetricCommandService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.BondRepository;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.FinancialMetricRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinancialMetricCommandServiceImpl implements FinancialMetricCommandService {
    private final FinancialMetricRepository financialMetricRepository;
    private final BondRepository bondRepository;

    public FinancialMetricCommandServiceImpl(FinancialMetricRepository financialMetricRepository,
                                             BondRepository bondRepository) {
        this.financialMetricRepository = financialMetricRepository;
        this.bondRepository = bondRepository;
    }

    @Override
    public Long handle(CreateFinancialMetricCommand command) {
        var bond = bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new BondNotFoundException(command.bondId());
        }

        FinancialMetric financialMetric = new FinancialMetric(command, bond.get());
        financialMetricRepository.save(financialMetric);
        return financialMetric.getId();
    }

    @Override
    public Optional<FinancialMetric> handle(UpdateFinancialMetricCommand command) {
        var financialMetric = financialMetricRepository.findById(command.id());
        if (financialMetric.isEmpty()) {
            return Optional.empty();
        }

        FinancialMetric updatedFinancialMetric = financialMetric.get().update(command);
        financialMetricRepository.save(updatedFinancialMetric);
        return Optional.of(updatedFinancialMetric);
    }

    @Override
    public void handle(DeleteFinancialMetricCommand command) {
        var financialMetric = financialMetricRepository.findById(command.id());
        if (financialMetric.isEmpty()) {
            throw new FinancialMetricNotFoundException(command.id());
        }

        financialMetricRepository.delete(financialMetric.get());
    }
}