package com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BondRepository extends JpaRepository<Bond, Long> {
    List<Bond> findByClient_Id(Long id);
}
