package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.profile.domain.model.aggregates.Profile;
import com.bonoflow.api.profile.domain.model.queries.GetAllProfilesQuery;
import com.bonoflow.api.profile.domain.model.queries.GetProfileByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetProfileByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileByUserIdQuery query);
}