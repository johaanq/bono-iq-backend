package com.bonoflow.api.bond.application.internal.outboundservices.acl;

import com.bonoflow.api.profile.domain.model.aggregates.Profile;
import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.profile.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalProfilesService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfilesService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Optional<Client> fetchClientById(Long clientId) {
        return profilesContextFacade.fetchClientById(clientId);
    }

    public Optional<Profile> fetchProfileByClientId(Long clientId) {
        return profilesContextFacade.fetchProfileByClientId(clientId);
    }


}