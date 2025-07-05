package com.bonoflow.api.profile.interfaces.rest.transform;

import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.profile.interfaces.rest.resources.ClientResource;

public class ClientResourceFromEntityAssembler {
    public static ClientResource toResourceFromEntity(Client entity) {
        return new ClientResource(
                entity.getId(),
                entity.getUserId());
    }
}