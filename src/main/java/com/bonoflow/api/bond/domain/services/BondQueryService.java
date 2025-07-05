package com.bonoflow.api.bond.domain.services;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.queries.GetAllBondsByClientIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllBondsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetBondByIdQuery;

import java.util.List;
import java.util.Optional;

public interface BondQueryService {
    List<Bond> handle(GetAllBondsQuery query);
    Optional<Bond> handle(GetBondByIdQuery query);
    List<Bond> handle(GetAllBondsByClientIdQuery query);
}
