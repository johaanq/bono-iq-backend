package com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.profile.domain.model.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Optional<Investor> findByUser_Id(Long userId);
}