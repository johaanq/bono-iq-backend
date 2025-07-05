package com.bonoflow.api.bond.application.internal.queryservices;

import com.bonoflow.api.bond.domain.model.entities.Investment;
import com.bonoflow.api.bond.domain.model.queries.GetAllInvestmentsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetInvestmentByIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllInvestmentsByInvestorIdQuery;
import com.bonoflow.api.bond.domain.services.InvestmentQueryService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.InvestmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestmentQueryServiceImpl implements InvestmentQueryService {
    private final InvestmentRepository investmentRepository;

    public InvestmentQueryServiceImpl(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    @Override
    public List<Investment> handle(GetAllInvestmentsQuery query) {
        return investmentRepository.findAll();
    }

    @Override
    public Optional<Investment> handle(GetInvestmentByIdQuery query) {
        return investmentRepository.findById(query.id());
    }

    @Override
    public List<Investment> handle(GetAllInvestmentsByInvestorIdQuery query) {
        return investmentRepository.findByInvestor_Id(query.investorId());
    }
}