package com.bonoflow.api.profile.domain.model.entities;

import com.bonoflow.api.bond.domain.model.valueobjects.BondCompounding;
import com.bonoflow.api.bond.domain.model.valueobjects.BondRateType;
import com.bonoflow.api.iam.domain.model.aggregates.User;
import com.bonoflow.api.profile.domain.model.commands.CreateConfigurationCommand;
import com.bonoflow.api.profile.domain.model.commands.UpdateConfigurationCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    // Moneda principal del sistema
    @NotNull
    @Column(nullable = false)
    private String currency;

    // Tipo de tasa de interés: EFECTIVA o NOMINAL
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "rate_type", nullable = false)
    private BondRateType rateType;

    // Capitalización (solo aplica si la tasa es nominal)
    @Enumerated(EnumType.STRING)
    @Column
    private BondCompounding compounding;

    // Constructor sin argumentos requerido por JPA
    public Configuration() {}

    public Configuration(CreateConfigurationCommand command, User user) {
        this.user = user;
        this.currency = command.currency();
        this.rateType = BondRateType.valueOf(command.rateType().toUpperCase());
        this.compounding = command.compounding() != null
                ? BondCompounding.valueOf(command.compounding().toUpperCase())
                : null;
    }

    public Configuration update(UpdateConfigurationCommand command) {
        this.currency = command.currency();
        this.rateType = BondRateType.valueOf(command.rateType().toUpperCase());
        this.compounding = command.compounding() != null
                ? BondCompounding.valueOf(command.compounding().toUpperCase())
                : null;
        return this;
    }

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}