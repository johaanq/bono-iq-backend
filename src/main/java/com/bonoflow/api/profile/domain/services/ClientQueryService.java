package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.profile.domain.model.queries.GetAllClientsQuery;
import com.bonoflow.api.profile.domain.model.queries.GetClientByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetClientByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ClientQueryService {
    List<Client> handle(GetAllClientsQuery query);
    Optional<Client> handle(GetClientByIdQuery query);
    Optional<Client> handle(GetClientByUserIdQuery query);
}