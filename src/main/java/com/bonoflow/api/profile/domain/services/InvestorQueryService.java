package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.profile.domain.model.entities.Investor;
import com.bonoflow.api.profile.domain.model.queries.GetAllInvestorsQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvestorQueryService {
    List<Investor> handle(GetAllInvestorsQuery query);
    Optional<Investor> handle(GetInvestorByIdQuery query);
    Optional<Investor> handle(GetInvestorByUserIdQuery query);
}