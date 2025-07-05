package com.bonoflow.api.profile.interfaces.rest.transform;

import com.bonoflow.api.profile.domain.model.commands.UpdateConfigurationCommand;
import com.bonoflow.api.profile.interfaces.rest.resources.UpdateConfigurationResource;

public class UpdateConfigurationCommandFromResourceAssembler {
    public static UpdateConfigurationCommand toCommand(Long id, UpdateConfigurationResource resource) {
        return new UpdateConfigurationCommand(
                id,
                resource.currency(),
                resource.rateType(),
                resource.compounding()
        );
    }
}