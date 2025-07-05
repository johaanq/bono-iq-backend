package com.bonoflow.api.profile.interfaces.rest.transform;

import com.bonoflow.api.profile.domain.model.entities.Configuration;
import com.bonoflow.api.profile.interfaces.rest.resources.ConfigurationResource;

public class ConfigurationResourceFromEntityAssembler {
    public static ConfigurationResource toResource(Configuration configuration) {
        return new ConfigurationResource(
                configuration.getId(),
                configuration.getUserId(),
                configuration.getCurrency(),
                configuration.getRateType(),
                configuration.getCompounding()
        );
    }
}