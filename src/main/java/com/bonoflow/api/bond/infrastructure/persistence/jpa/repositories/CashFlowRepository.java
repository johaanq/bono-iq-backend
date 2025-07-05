package com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CashFlowRepository extends JpaRepository<CashFlow, Long> {
    List<CashFlow> findByBond_Id(Long bondId);
    @Query("SELECT c FROM CashFlow c JOIN c.bond b WHERE b.client.id = :clientId")
    List<CashFlow> findAllByClientId(@Param("clientId") Long clientId);
}