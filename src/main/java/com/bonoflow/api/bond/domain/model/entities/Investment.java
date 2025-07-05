// src/main/java/com/bonoflow/api/bond/domain/model/entities/Investment.java
package com.bonoflow.api.bond.domain.model.entities;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.commands.CreateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateInvestmentCommand;
import com.bonoflow.api.profile.domain.model.entities.Investor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "investor_id", nullable = false)
    private Investor investor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bond_id", nullable = false)
    private Bond bond;

    @NotNull
    @Column(nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "investment_date", nullable = false)
    private LocalDate investmentDate;

    public Investment() {}

    public Investment(Investor investor, Bond bond, Double amount, LocalDate investmentDate) {
        this.investor = investor;
        this.bond = bond;
        this.amount = amount;
        this.investmentDate = investmentDate;
    }

    // Constructor para crear desde comando
    public Investment(CreateInvestmentCommand command, Investor investor, Bond bond) {
        this.investor = investor;
        this.bond = bond;
        this.amount = command.amount();
        this.investmentDate = command.investmentDate();
    }

    // Método para actualizar desde comando
    public Investment update(UpdateInvestmentCommand command) {
        this.amount = command.amount();
        this.investmentDate = command.investmentDate();
        return this;
    }

    public Long getInvestorId() {
        return investor.getId();
    }

    public Long getBondId() {
        return bond.getId();
    }
}