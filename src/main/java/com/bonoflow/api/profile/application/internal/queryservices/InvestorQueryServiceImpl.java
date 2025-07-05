package com.bonoflow.api.profile.application.internal.queryservices;

import com.bonoflow.api.profile.domain.model.entities.Investor;
import com.bonoflow.api.profile.domain.model.queries.GetAllInvestorsQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByUserIdQuery;
import com.bonoflow.api.profile.domain.services.InvestorQueryService;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorQueryServiceImpl implements InvestorQueryService {
    private final InvestorRepository investorRepository;

    public InvestorQueryServiceImpl(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Override
    public Optional<Investor> handle(GetInvestorByIdQuery query) {
        return investorRepository.findById(query.id());
    }

    @Override
    public Optional<Investor> handle(GetInvestorByUserIdQuery query) {
        return investorRepository.findByUser_Id(query.userId());
    }

    @Override
    public List<Investor> handle(GetAllInvestorsQuery query) {
        return investorRepository.findAll();
    }
}