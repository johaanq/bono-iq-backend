package com.bonoflow.api.bond.application.internal.commandservices;

import com.bonoflow.api.bond.domain.exceptions.BondNotFoundException;
import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.commands.CreateBondCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteBondCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateBondCommand;
import com.bonoflow.api.bond.domain.services.BondCommandService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.BondRepository;
import com.bonoflow.api.bond.application.internal.outboundservices.acl.ExternalProfilesService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondCommandServiceImpl implements BondCommandService {
    private final BondRepository bondRepository;
    private final ExternalProfilesService externalProfilesService;

    public BondCommandServiceImpl(BondRepository bondRepository, ExternalProfilesService externalProfilesService) {
        this.bondRepository = bondRepository;
        this.externalProfilesService = externalProfilesService;
    }

    @Override
    public Long handle(CreateBondCommand command) {
        var client = externalProfilesService.fetchClientById(command.clientId());
        if (client.isEmpty()) {
            throw new BondNotFoundException(command.clientId());
        }

        Bond bond = new Bond(command, client.get());
        bondRepository.save(bond);
        return bond.getId();
    }

    @Override
    public Optional<Bond> handle(UpdateBondCommand command) {
        var bond = bondRepository.findById(command.id());
        if (bond.isEmpty()) {
            return Optional.empty();
        }

        Bond updatedBond = bond.get().update(command);
        bondRepository.save(updatedBond);
        return Optional.of(updatedBond);
    }

    @Override
    public void handle(DeleteBondCommand command) {
        var bond = bondRepository.findById(command.id());
        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond not found for ID: " + command.id());
        }

        bondRepository.delete(bond.get());
    }
}