package com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories;

import com.bonoflow.api.profile.domain.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser_Id(Long userId);
}