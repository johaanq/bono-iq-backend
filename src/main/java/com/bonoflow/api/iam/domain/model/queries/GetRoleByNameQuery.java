package com.bonoflow.api.iam.domain.model.queries;

import com.bonoflow.api.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}