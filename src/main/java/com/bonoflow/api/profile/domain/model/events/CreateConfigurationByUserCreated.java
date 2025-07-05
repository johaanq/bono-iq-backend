package com.bonoflow.api.profile.domain.model.events;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateConfigurationByUserCreated extends ApplicationEvent {

    private final Long userId;
    private final User user;

    public CreateConfigurationByUserCreated(Object source, Long userId, User user) {
        super(source);
        this.userId = userId;
        this.user = user;
    }
}