package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.profile.domain.model.entities.Configuration;
import com.bonoflow.api.profile.domain.model.queries.GetConfigurationByUserIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetConfigurationByIdQuery;


import java.util.Optional;

public interface ConfigurationQueryService {
    Optional<Configuration> handle(GetConfigurationByIdQuery query);
    Optional<Configuration> handle(GetConfigurationByUserIdQuery query);
}