package com.bonoflow.api.profile.interfaces.acl;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.domain.model.aggregates.Profile;
import com.bonoflow.api.profile.domain.model.commands.CreateClientCommand;
import com.bonoflow.api.profile.domain.model.commands.CreateInvestorCommand;
import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.profile.domain.model.entities.Investor;
import com.bonoflow.api.profile.domain.model.queries.GetClientByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.bonoflow.api.profile.domain.services.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilesContextFacade {

    private final ProfileQueryService profileQueryService;
    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;
    private final InvestorCommandService investorCommandService;
    private final InvestorQueryService investorQueryService;

    public ProfilesContextFacade(ProfileQueryService profileQueryService,
                                 ClientCommandService clientCommandService,
                                 ClientQueryService clientQueryService,
                                 InvestorCommandService investorCommandService,
                                 InvestorQueryService investorQueryService) {
        this.profileQueryService = profileQueryService;
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
        this.investorCommandService = investorCommandService;
        this.investorQueryService = investorQueryService;
    }

    public Optional<Profile> fetchProfileByUserId(Long userId) {
        return profileQueryService.handle(new GetProfileByUserIdQuery(userId));
    }

    public Optional<Client> fetchClientById(Long clientId) {
        var getClientByIdQuery = new GetClientByIdQuery(clientId);
        return clientQueryService.handle(getClientByIdQuery);
    }

    public Optional<Investor> fetchInvestorById(Long investorId) {
        var getInvestorByIdQuery = new GetInvestorByIdQuery(investorId);
        return investorQueryService.handle(getInvestorByIdQuery);
    }

    public Optional<Profile> fetchProfileByClientId(Long clientId) {
        var clientProfileQuery = new GetClientByIdQuery(clientId);
        var client = clientQueryService.handle(clientProfileQuery);
        if (client.isEmpty()) return Optional.empty();
        Long userId = client.get().getUserId();
        var profileQuery = new GetProfileByUserIdQuery(userId);
        return profileQueryService.handle(profileQuery);
    }

    public Optional<Profile> fetchInvestorByInvestorId(Long clientId) {
        var investorProfileQuery = new GetInvestorByIdQuery(clientId);
        var investor = investorQueryService.handle(investorProfileQuery);
        if (investor.isEmpty()) return Optional.empty();
        Long userId = investor.get().getUserId();
        var profileQuery = new GetProfileByUserIdQuery(userId);
        return profileQueryService.handle(profileQuery);
    }

    public Long createClient(Long userId, User user) {
        return clientCommandService.handle(new CreateClientCommand(userId), user);
    }

    public Long createInvestor(Long userId, User user) {
        return investorCommandService.handle(new CreateInvestorCommand(userId), user);
    }


}