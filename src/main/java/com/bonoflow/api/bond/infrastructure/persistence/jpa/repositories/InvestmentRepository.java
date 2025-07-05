package com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.bond.domain.model.entities.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByInvestor_Id(Long investorId);
}