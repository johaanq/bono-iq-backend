package com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.profile.domain.model.entities.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Optional<Configuration> findByUser_Id(Long id);
}
