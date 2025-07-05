package com.bonoflow.api.bond.application.internal.commandservices;

import com.bonoflow.api.bond.domain.exceptions.BondNotFoundException;
import com.bonoflow.api.bond.domain.exceptions.InvestmentNotFoundException;
import com.bonoflow.api.bond.domain.model.commands.CreateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteInvestmentCommand;
import com.bonoflow.api.bond.domain.model.entities.Investment;
import com.bonoflow.api.bond.domain.services.InvestmentCommandService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.BondRepository;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.InvestmentRepository;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvestmentCommandServiceImpl implements InvestmentCommandService {
    private final InvestmentRepository investmentRepository;
    private final BondRepository bondRepository;
    private final InvestorRepository investorRepository;

    public InvestmentCommandServiceImpl(InvestmentRepository investmentRepository,
                                        BondRepository bondRepository,
                                        InvestorRepository investorRepository) {
        this.investmentRepository = investmentRepository;
        this.bondRepository = bondRepository;
        this.investorRepository = investorRepository;
    }

    @Override
    public Long handle(CreateInvestmentCommand command) {
        var investor = investorRepository.findById(command.investorId());
        if (investor.isEmpty()) {
            throw new RuntimeException("Investor not found: " + command.investorId());
        }
        var bond = bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new BondNotFoundException(command.bondId());
        }
        Investment investment = new Investment(command, investor.get(), bond.get());
        investmentRepository.save(investment);
        return investment.getId();
    }

    @Override
    public Optional<Investment> handle(UpdateInvestmentCommand command) {
        var investment = investmentRepository.findById(command.id());
        if (investment.isEmpty()) {
            return Optional.empty();
        }
        Investment updatedInvestment = investment.get().update(command);
        investmentRepository.save(updatedInvestment);
        return Optional.of(updatedInvestment);
    }

    @Override
    public void handle(DeleteInvestmentCommand command) {
        var investment = investmentRepository.findById(command.id());
        if (investment.isEmpty()) throw new InvestmentNotFoundException(command.id());
        investmentRepository.delete(investment.get());
    }
}