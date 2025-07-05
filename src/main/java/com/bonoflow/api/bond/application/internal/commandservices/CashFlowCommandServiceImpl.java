package com.bonoflow.api.bond.application.internal.commandservices;

import com.bonoflow.api.bond.domain.exceptions.BondNotFoundException;
import com.bonoflow.api.bond.domain.exceptions.CashFlowNotFoundException;
import com.bonoflow.api.bond.domain.model.commands.CreateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteCashFlowCommand;
import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import com.bonoflow.api.bond.domain.services.CashFlowCommandService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.BondRepository;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.CashFlowRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashFlowCommandServiceImpl implements CashFlowCommandService {
    private final CashFlowRepository cashFlowRepository;
    private final BondRepository bondRepository;

    public CashFlowCommandServiceImpl(CashFlowRepository cashFlowRepository,
                                      BondRepository bondRepository) {
        this.cashFlowRepository = cashFlowRepository;
        this.bondRepository = bondRepository;
    }

    @Override
    public Long handle(CreateCashFlowCommand command) {
        var bond = bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new BondNotFoundException(command.bondId());
        }
        CashFlow cashFlow = new CashFlow(command, bond.get());
        cashFlowRepository.save(cashFlow);
        return cashFlow.getId();
    }

    @Override
    public Optional<CashFlow> handle(UpdateCashFlowCommand command) {
        var cashFlow = cashFlowRepository.findById(command.id());
        if (cashFlow.isEmpty()) {
            return Optional.empty();
        }

        CashFlow updatedCashFlow = cashFlow.get().update(command);
        cashFlowRepository.save(updatedCashFlow);
        return Optional.of(updatedCashFlow);
    }

    @Override
    public void handle(DeleteCashFlowCommand command) {
        var cashFlow = cashFlowRepository.findById(command.id());
        if(cashFlow.isEmpty()) throw new CashFlowNotFoundException(command.id());
        cashFlowRepository.delete(cashFlow.get());
    }
}