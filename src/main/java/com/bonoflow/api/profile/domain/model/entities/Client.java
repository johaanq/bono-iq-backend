package com.bonoflow.api.profile.domain.model.entities;

import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.domain.model.commands.CreateClientCommand;
import com.bonoflow.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Client() {
    }

    public Client(CreateClientCommand command, User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }
}