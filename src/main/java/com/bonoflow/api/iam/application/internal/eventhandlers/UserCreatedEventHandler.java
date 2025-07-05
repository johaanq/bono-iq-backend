package com.bonoflow.api.iam.application.internal.eventhandlers;

import com.bonoflow.api.profile.application.internal.commandservices.ConfigurationCommandServiceImpl;
import com.bonoflow.api.profile.domain.model.commands.CreateConfigurationCommand;
import com.bonoflow.api.profile.domain.model.events.CreateConfigurationByUserCreated;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedEventHandler {

    private final ConfigurationCommandServiceImpl configurationCommandService;

    public UserCreatedEventHandler(ConfigurationCommandServiceImpl configurationCommandService) {
        this.configurationCommandService = configurationCommandService;
    }

    @EventListener
    public void onUserCreated(CreateConfigurationByUserCreated event) {
        // Valores por defecto
        String defaultCurrency = "PEN";
        String defaultRateType = "EFFECTIVE";
        String defaultCompounding = null; // o el valor que desees

        var command = new CreateConfigurationCommand(
                event.getUserId(),
                defaultCurrency,
                defaultRateType,
                defaultCompounding
        );
        configurationCommandService.handle(command);
    }
}