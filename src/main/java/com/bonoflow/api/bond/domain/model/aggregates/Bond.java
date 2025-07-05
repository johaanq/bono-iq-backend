package com.bonoflow.api.bond.domain.model.aggregates;

import com.bonoflow.api.bond.domain.model.commands.CreateBondCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateBondCommand;
import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import com.bonoflow.api.bond.domain.model.entities.Investment;
import com.bonoflow.api.bond.domain.model.valueobjects.BondRateType;
import com.bonoflow.api.bond.domain.model.valueobjects.BondCompounding;
import com.bonoflow.api.bond.domain.model.valueobjects.BondPaymentFrequency;
import com.bonoflow.api.bond.domain.model.valueobjects.BondGraceType;
import com.bonoflow.api.profile.domain.model.entities.Client;
import com.bonoflow.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "bonds")
public class Bond extends AuditableAbstractAggregateRoot<Bond> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el cliente dueño del bono
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Nombre del bono
    @NotNull
    @Column(nullable = false)
    private String name;

    // Valor nominal del bono
    @NotNull
    @Column(name = "face_value", nullable = false)
    private Double faceValue;

    // Tasa de interés del bono
    @NotNull
    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    // Tipo de tasa de interés (fija, variable, etc.)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "rate_type", nullable = false)
    private BondRateType rateType;

    // Tipo de capitalización de intereses (anual, semestral, etc.)
    @Enumerated(EnumType.STRING)
    @Column
    private BondCompounding compounding;

    // Frecuencia de pago (mensual, anual, etc.)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "payment_frequency", nullable = false)
    private BondPaymentFrequency paymentFrequency;

    // Moneda en la que está emitido el bono
    @NotNull
    @Column(nullable = false)
    private String currency;

    // Tipo de periodo de gracia (total, parcial, etc.)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "grace_type", nullable = false)
    private BondGraceType graceType;

    // Cantidad de periodos de gracia
    @NotNull
    @Column(name = "grace_period", nullable = false)
    private Integer gracePeriod;

    // Fecha de emisión del bono
    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    // Fecha de vencimiento del bono
    @NotNull
    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    // Gastos de emisión del bono
    @Basic
    @Column(name = "issuance_expenses")
    private Double issuanceExpenses;

    // Gastos de colocación del bono
    @Basic
    @Column(name = "placement_expenses")
    private Double placementExpenses;

    // Gastos de estructuración del bono
    @Basic
    @Column(name = "structuring_expenses")
    private Double structuringExpenses;

    // Gastos de Cavali (registro y custodia)
    @Basic
    @Column(name = "cavali_expenses")
    private Double cavaliExpenses;

    // Tablas que se borran cuando se borra el bono

    @NotNull
    @Column(name = "market_rate", nullable = false)
    private Double marketRate;

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashFlow> cashFlows = new ArrayList<>();

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinancialMetric> financialMetrics = new ArrayList<>();

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments = new ArrayList<>();



    public Bond() {}

    public Bond(CreateBondCommand command, Client client) {
        this.client = client;
        this.name = command.name();
        this.faceValue = command.faceValue();
        this.interestRate = command.interestRate();
        this.rateType = BondRateType.valueOf(command.rateType().toUpperCase());
        this.compounding = BondCompounding.valueOf(command.compounding().toUpperCase());
        this.paymentFrequency = BondPaymentFrequency.valueOf(command.paymentFrequency().toUpperCase());
        this.currency = command.currency();
        this.graceType = BondGraceType.valueOf(command.graceType().toUpperCase());
        this.gracePeriod = command.gracePeriod();
        this.issueDate = command.issueDate();
        this.maturityDate = command.maturityDate();
        this.issuanceExpenses = command.issuanceExpenses();
        this.placementExpenses = command.placementExpenses();
        this.structuringExpenses = command.structuringExpenses();
        this.cavaliExpenses = command.cavaliExpenses();
        this.marketRate = command.marketRate();
    }

    public Bond update(UpdateBondCommand command) {
        this.name = command.name();
        this.faceValue = command.faceValue();
        this.interestRate = command.interestRate();
        this.rateType = BondRateType.valueOf(command.rateType().toUpperCase());
        this.compounding = BondCompounding.valueOf(command.compounding().toUpperCase());
        this.paymentFrequency = BondPaymentFrequency.valueOf(command.paymentFrequency().toUpperCase());
        this.currency = command.currency();
        this.graceType = BondGraceType.valueOf(command.graceType().toUpperCase());
        this.gracePeriod = command.gracePeriod();
        this.issueDate = command.issueDate();
        this.maturityDate = command.maturityDate();
        this.cashFlows.clear();
        this.financialMetrics.clear();
        this.issuanceExpenses = command.issuanceExpenses();
        this.placementExpenses = command.placementExpenses();
        this.structuringExpenses = command.structuringExpenses();
        this.cavaliExpenses = command.cavaliExpenses();
        this.marketRate = command.marketRate();
        return this;
    }

    public Long getClientId() {
        return client.getId();
    }
}