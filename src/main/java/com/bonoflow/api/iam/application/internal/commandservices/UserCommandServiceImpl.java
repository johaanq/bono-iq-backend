package com.bonoflow.api.iam.application.internal.commandservices;

import com.bonoflow.api.iam.application.internal.outboundservices.acl.ExternalProfileRoleService;
import com.bonoflow.api.iam.application.internal.outboundservices.hashing.HashingService;
import com.bonoflow.api.iam.application.internal.outboundservices.tokens.TokenService;
import com.bonoflow.api.iam.domain.exceptions.RoleNotFoundException;
import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.iam.domain.model.commands.SignInCommand;
import com.bonoflow.api.iam.domain.model.commands.SignUpCommand;
import com.bonoflow.api.iam.domain.services.UserCommandService;
import com.bonoflow.api.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.bonoflow.api.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.bonoflow.api.profile.domain.model.events.CreateConfigurationByUserCreated;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final ExternalProfileRoleService externalProfileRoleService;
    private final ApplicationEventPublisher eventPublisher;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService,
                                  TokenService tokenService,
                                  RoleRepository roleRepository,
                                  ExternalProfileRoleService externalProfileRoleService,
                                  ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.externalProfileRoleService = externalProfileRoleService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        var roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RoleNotFoundException(role.getStringName())))
                .toList();
        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);

        // Publicar el evento para que el handler lo escuche
        eventPublisher.publishEvent(new CreateConfigurationByUserCreated(this, user.getId(), user));

        roles.forEach(role -> {
            if (role.getStringName().equals("ROLE_CLIENT")) {
                externalProfileRoleService.createClient(user.getId(), user);
            } else if (role.getStringName().equals("ROLE_INVESTOR")) {
                externalProfileRoleService.createInvestor(user.getId(), user);
            }
        });
        return userRepository.findByUsername(command.username());
    }
}