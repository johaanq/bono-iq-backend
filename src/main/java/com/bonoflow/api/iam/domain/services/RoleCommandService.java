package com.bonoflow.api.iam.domain.services;

import com.bonoflow.api.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}