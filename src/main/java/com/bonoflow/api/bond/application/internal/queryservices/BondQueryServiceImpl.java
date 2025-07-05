package com.bonoflow.api.bond.application.internal.queryservices;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.queries.GetAllBondsByClientIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllBondsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetBondByIdQuery;
import com.bonoflow.api.bond.domain.services.BondQueryService;
import com.bonoflow.api.bond.infrastructure.persistence.jpa.repositories.BondRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BondQueryServiceImpl implements BondQueryService {
    private final BondRepository bondRepository;

    public BondQueryServiceImpl(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    @Override
    public List<Bond> handle(GetAllBondsQuery query) {
        return bondRepository.findAll();
    }

    @Override
    public Optional<Bond> handle(GetBondByIdQuery query) {
        return bondRepository.findById(query.id());
    }

    @Override
    public List<Bond> handle(GetAllBondsByClientIdQuery query) {
        return bondRepository.findByClient_Id(query.clientId());
    }
}