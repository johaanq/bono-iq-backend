package com.bonoflow.api.bond.application.internal.queryservices;

import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import com.bonoflow.api.bond.domain.model.queries.GetAllFinancialMetricsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetFinancialMetricByBondIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetFinancialMetricByIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllFinancialMetricsByBondIdQuery;
import com.bonoflow.api.bond.domain.services.FinancialMetricQueryService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.FinancialMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialMetricQueryServiceImpl implements FinancialMetricQueryService {
    private final FinancialMetricRepository financialMetricRepository;

    public FinancialMetricQueryServiceImpl(FinancialMetricRepository financialMetricRepository) {
        this.financialMetricRepository = financialMetricRepository;
    }

    @Override
    public List<FinancialMetric> handle(GetAllFinancialMetricsQuery query) {
        return financialMetricRepository.findAll();
    }

    @Override
    public Optional<FinancialMetric> handle(GetFinancialMetricByIdQuery query) {
        return financialMetricRepository.findById(query.id());
    }

    @Override
    public List<FinancialMetric> handle(GetAllFinancialMetricsByBondIdQuery query) {
        return financialMetricRepository.findAllByBond_Id(query.bondId());
    }

    @Override
    public Optional<FinancialMetric> handle(GetFinancialMetricByBondIdQuery query) {
        return financialMetricRepository.findByBond_Id(query.bondId());
    }
}