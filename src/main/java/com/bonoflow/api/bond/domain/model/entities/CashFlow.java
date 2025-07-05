package com.bonoflow.api.bond.domain.model.entities;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.commands.CreateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateCashFlowCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "cash_flows")
public class CashFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bond_id", nullable = false)
    private Bond bond;

    @NotNull
    @Column(nullable = false)
    private Integer period;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @NotNull
    @Column(nullable = false)
    private Double interest;

    @NotNull
    @Column(nullable = false)
    private Double amortization;

    @NotNull
    @Column(nullable = false)
    private Double fixedInstallment;

    @NotNull
    @Column(name = "investor_flow")
    private Double investorFlow;

    @NotNull
    @Column(nullable = false)
    private Double installment;

    @NotNull
    @Column(name = "final_balance", nullable = false)
    private Double finalBalance;

    @NotNull
    @Column(name = "expenses", nullable = false)
    private Double expenses;

    public CashFlow() {}

    public CashFlow(CreateCashFlowCommand command, Bond bond) {
        this.bond = bond;
        this.period = command.period();
        this.date = command.date();
        this.initialBalance = command.initialBalance();
        this.interest = command.interest();
        this.amortization = command.amortization();
        this.installment = command.installment();
        this.finalBalance = command.finalBalance();
        this.fixedInstallment = command.fixedInstallment();
        this.expenses = command.expenses();
        this.investorFlow = command.investorFlow();
    }

    public CashFlow update(UpdateCashFlowCommand command) {
        this.period = command.period();
        this.date = command.date();
        this.initialBalance = command.initialBalance();
        this.interest = command.interest();
        this.amortization = command.amortization();
        this.installment = command.installment();
        this.finalBalance = command.finalBalance();
        this.fixedInstallment = command.fixedInstallment();
        this.expenses = command.expenses();
        this.investorFlow = command.investorFlow();
        return this;
    }

    public Long getBondId() {
        return bond.getId();
    }
}