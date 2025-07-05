package com.bonoflow.api.profile.application.internal.queryservices;

import com.bonoflow.api.profile.domain.model.entities.Configuration;

import com.bonoflow.api.profile.domain.model.queries.GetConfigurationByUserIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetConfigurationByIdQuery;
import com.bonoflow.api.profile.domain.services.ConfigurationQueryService;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.ConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationQueryServiceImpl implements ConfigurationQueryService {
    private final ConfigurationRepository configurationRepository;

    public ConfigurationQueryServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public Optional<Configuration> handle(GetConfigurationByIdQuery query) {
        return configurationRepository.findById(query.id());
    }

    @Override
    public Optional<Configuration> handle(GetConfigurationByUserIdQuery query) {
        return configurationRepository.findByUser_Id(query.userId());
    }
}