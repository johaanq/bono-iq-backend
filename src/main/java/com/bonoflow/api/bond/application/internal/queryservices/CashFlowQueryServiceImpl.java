package com.bonoflow.api.bond.application.internal.queryservices;

import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsByClientIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetCashFlowByIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsByBondId;
import com.bonoflow.api.bond.domain.services.CashFlowQueryService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.CashFlowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashFlowQueryServiceImpl implements CashFlowQueryService {
    private final CashFlowRepository cashFlowRepository;

    public CashFlowQueryServiceImpl(CashFlowRepository cashFlowRepository) {
        this.cashFlowRepository = cashFlowRepository;
    }

    @Override
    public List<CashFlow> handle(GetAllCashFlowsQuery query) {
        return cashFlowRepository.findAll();
    }

    @Override
    public Optional<CashFlow> handle(GetCashFlowByIdQuery query) {
        return cashFlowRepository.findById(query.id());
    }

    @Override
    public List<CashFlow> handle(GetAllCashFlowsByBondId query) {
        return cashFlowRepository.findByBond_Id(query.bondId());
    }

    @Override
    public List<CashFlow> handle(GetAllCashFlowsByClientIdQuery query) {
        return cashFlowRepository.findAllByClientId(query.clientId());
    }
}