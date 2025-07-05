package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.entities.Investment;
import com.bonoflow.api.bond.domain.model.queries.GetAllInvestmentsByInvestorIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllInvestmentsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetInvestmentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvestmentQueryService {
    List<Investment> handle(GetAllInvestmentsQuery query);
    Optional<Investment> handle(GetInvestmentByIdQuery query);
    List<Investment> handle(GetAllInvestmentsByInvestorIdQuery query);
}