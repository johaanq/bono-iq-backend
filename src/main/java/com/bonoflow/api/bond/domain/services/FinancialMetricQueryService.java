package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import com.bonoflow.api.bond.domain.model.queries.GetAllFinancialMetricsByBondIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllFinancialMetricsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetFinancialMetricByBondIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetFinancialMetricByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FinancialMetricQueryService {
    List<FinancialMetric> handle(GetAllFinancialMetricsQuery query);
    List<FinancialMetric> handle(GetAllFinancialMetricsByBondIdQuery query);
    Optional<FinancialMetric> handle(GetFinancialMetricByIdQuery query);
    Optional<FinancialMetric> handle(GetFinancialMetricByBondIdQuery query);
}