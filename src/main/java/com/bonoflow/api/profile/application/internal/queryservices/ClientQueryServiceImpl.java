package com.bonoflow.api.profile.application.internal.queryservices;

import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.profile.domain.model.queries.GetAllClientsQuery;
import com.bonoflow.api.profile.domain.model.queries.GetClientByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetClientByUserIdQuery;
import com.bonoflow.api.profile.domain.services.ClientQueryService;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientQueryServiceImpl implements ClientQueryService {
    private final ClientRepository ClientRepository;

    public ClientQueryServiceImpl(ClientRepository ClientRepository) {
        this.ClientRepository = ClientRepository;
    }

    @Override
    public Optional<Client> handle(GetClientByIdQuery query) {
        return ClientRepository.findById(query.id());
    }

    @Override
    public Optional<Client> handle(GetClientByUserIdQuery query) {
        return ClientRepository.findByUser_Id(query.userId());
    }

    @Override
    public List<Client> handle(GetAllClientsQuery query) {
        return ClientRepository.findAll();
    }

}