package com.bonoflow.api.profile.application.internal.commandservices;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.domain.exceptions.InvestorNotFoundException;
import com.bonoflow.api.profile.domain.exceptions.UserNotFoundException;
import com.bonoflow.api.profile.domain.model.commands.CreateInvestorCommand;
import com.bonoflow.api.profile.domain.model.commands.DeleteInvestorCommand;
import com.bonoflow.api.profile.domain.model.entities.Investor;
import com.bonoflow.api.profile.domain.services.InvestorCommandService;
import com.bonoflow.api.profile.infrastructure.persistence.jpa.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

@Service
public class InvestorCommandServiceImpl implements InvestorCommandService {
    private final InvestorRepository investorRepository;

    public InvestorCommandServiceImpl(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Override
    public Long handle(CreateInvestorCommand command, User user) {
        var sameUser = investorRepository.findByUser_Id(command.userId());
        if (sameUser.isPresent()) {
            throw new UserNotFoundException(command.userId());
        }
        var investor = new Investor(command, user);
        investorRepository.save(investor);
        return investor.getId();
    }

    @Override
    public void handle(DeleteInvestorCommand command) {
        var investor = investorRepository.findById(command.id());
        if (investor.isEmpty()) {
            throw new InvestorNotFoundException(command.id());
        }
        investorRepository.delete(investor.get());
    }
}