package com.bonoflow.api.profile.application.internal.commandservices;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.application.internal.outboundservices.acl.ExternalUserService;
import com.bonoflow.api.profile.domain.exceptions.ClientNotFoundException;
import com.bonoflow.api.profile.domain.exceptions.UserNotFoundException;
import com.bonoflow.api.profile.domain.model.commands.CreateClientCommand;
import com.bonoflow.api.profile.domain.model.commands.DeleteClientCommand;
import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.profile.domain.services.ClientCommandService;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientCommandServiceImpl implements ClientCommandService {
    private final ClientRepository ClientRepository;

    public ClientCommandServiceImpl(ClientRepository ClientRepository) {
        this.ClientRepository = ClientRepository;
    }

    @Override
    public Long handle(CreateClientCommand command, User user) {
        var sameUser = ClientRepository.findByUser_Id(command.userId());
        if (sameUser.isPresent()) {
            throw new UserNotFoundException(command.userId());
        }
        var Client = new Client(command, user);
        ClientRepository.save(Client);
        return Client.getId();
    }

    @Override
    public void handle(DeleteClientCommand command) {
        var Client = ClientRepository.findById(command.id());
        if (Client.isEmpty()) {
            throw new ClientNotFoundException(command.id());
        }
        ClientRepository.delete(Client.get());
    }
}