package com.bonoflow.api.profile.application.internal.commandservices;

import com.bonoflow.api.profile.application.internal.outboundservices.acl.ExternalUserService;
import com.bonoflow.api.profile.domain.exceptions.UserNotFoundException;
import com.bonoflow.api.profile.domain.model.commands.CreateConfigurationCommand;
import com.bonoflow.api.profile.domain.model.commands.UpdateConfigurationCommand;
import com.bonoflow.api.profile.domain.model.commands.DeleteConfigurationCommand;
import com.bonoflow.api.profile.domain.model.entities.Configuration;
import com.bonoflow.api.profile.domain.services.ConfigurationCommandService;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.ConfigurationRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationCommandServiceImpl implements ConfigurationCommandService {

    private final ConfigurationRepository configurationRepository;
    private final ExternalUserService externalUserService;

    public ConfigurationCommandServiceImpl(ConfigurationRepository configurationRepository,
                                           ExternalUserService externalUserService) {
        this.configurationRepository = configurationRepository;
        this.externalUserService = externalUserService;
    }

    @Override
    public Long handle(CreateConfigurationCommand command) {
        var user = externalUserService.fetchUserById(command.userId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(command.userId());
        }
        Configuration configuration = new Configuration(command, user.get());
        configurationRepository.save(configuration);
        return configuration.getId();
    }

    @Override
    public Optional<Configuration> handle(UpdateConfigurationCommand command) {
        var configuration = configurationRepository.findById(command.id());
        if (configuration.isEmpty()) {
            return Optional.empty();
        }

        Configuration updatedConfiguration = configuration.get().update(command);
        configurationRepository.save(updatedConfiguration);
        return Optional.of(updatedConfiguration);
    }

    @Override
    public void handle(DeleteConfigurationCommand command) {
        var configuration = configurationRepository.findById(command.id());
        if (configuration.isEmpty()) {
            throw new IllegalArgumentException("Configuration not found for ID: " + command.id());
        }

        configurationRepository.delete(configuration.get());
    }
}