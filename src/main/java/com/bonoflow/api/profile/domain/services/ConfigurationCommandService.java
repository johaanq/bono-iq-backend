package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.profile.domain.model.commands.CreateConfigurationCommand;
import com.bonoflow.api.profile.domain.model.commands.DeleteConfigurationCommand;
import com.bonoflow.api.profile.domain.model.commands.UpdateConfigurationCommand;
import com.bonoflow.api.profile.domain.model.entities.Configuration;

import java.util.Optional;

public interface ConfigurationCommandService {
    Long handle(CreateConfigurationCommand command);
    Optional<Configuration> handle(UpdateConfigurationCommand command);
    void handle(DeleteConfigurationCommand command);

}