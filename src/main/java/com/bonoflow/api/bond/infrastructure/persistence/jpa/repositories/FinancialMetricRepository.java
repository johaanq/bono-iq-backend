package com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialMetricRepository extends JpaRepository<FinancialMetric, Long> {
    Optional<FinancialMetric> findByBond_Id(Long bondId);
    List<FinancialMetric> findAllByBond_Id(Long bondId);
}