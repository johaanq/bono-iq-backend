package com.bonoflow.api.profile.interfaces.rest.transform;

import com.bonoflow.api.profile.domain.model.entities.Investor;
import com.bonoflow.api.profile.interfaces.rest.resources.InvestorResource;

public class InvestorResourceFromEntityAssembler {
    public static InvestorResource toResourceFromEntity(Investor entity) {
        return new InvestorResource(
                entity.getId(),
                entity.getUserId());
    }
}