package com.bonoflow.api.iam.application.internal.outboundservices.acl;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalProfileRoleService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileRoleService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Long createClient(Long userId, User user) {
        return profilesContextFacade.createClient(userId, user);
    }

    public Long createInvestor(Long userId, User user) {
        return profilesContextFacade.createInvestor(userId, user);
    }


}