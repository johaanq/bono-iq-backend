package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.domain.model.commands.CreateClientCommand;
import com.bonoflow.api.profile.domain.model.commands.DeleteClientCommand;

import java.util.Optional;

public interface ClientCommandService {
    Long handle(CreateClientCommand createClientCommand,User user);
    void handle(DeleteClientCommand command);
}