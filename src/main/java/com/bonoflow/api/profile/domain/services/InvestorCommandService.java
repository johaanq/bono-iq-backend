package com.bonoflow.api.profile.domain.services;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.domain.model.commands.CreateInvestorCommand;
import com.bonoflow.api.profile.domain.model.commands.DeleteInvestorCommand;

public interface InvestorCommandService {
    Long handle(CreateInvestorCommand createInvestorCommand, User user);
    void handle(DeleteInvestorCommand command);
}