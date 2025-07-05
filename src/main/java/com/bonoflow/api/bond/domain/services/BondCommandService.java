package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.commands.CreateBondCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteBondCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateBondCommand;
import com.bonoflow.api.iam.domain.model.aggregates.User;



import java.util.Optional;

public interface BondCommandService {
    Long handle(CreateBondCommand command);
    Optional<Bond> handle(UpdateBondCommand command);
    void handle(DeleteBondCommand command);
}