package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsByClientIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetCashFlowByIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsByBondId;

import java.util.List;
import java.util.Optional;

public interface CashFlowQueryService {
    List<CashFlow> handle(GetAllCashFlowsQuery query);
    Optional<CashFlow> handle(GetCashFlowByIdQuery query);
    List<CashFlow> handle(GetAllCashFlowsByBondId query);
    List<CashFlow> handle(GetAllCashFlowsByClientIdQuery query);
}