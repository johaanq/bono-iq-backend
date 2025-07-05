package com.bonoflow.api.bond.domain.model.entities;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.commands.CreateFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateFinancialMetricCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "financial_metrics")
public class FinancialMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bond_id", nullable = false)
    private Bond bond;

    @NotNull
    @Column(nullable = false)
    private Double tcea;

    @NotNull
    @Column(nullable = false)
    private Double trea;

    @NotNull
    @Column(nullable = false)
    private Double duration;

    @NotNull
    @Column(name = "modified_duration", nullable = false)
    private Double modifiedDuration;

    @NotNull
    @Column(nullable = false)
    private Double convexity;

    @NotNull
    @Column(name = "market_price", nullable = false)
    private Double marketPrice;

    @NotNull
    @Column(name = "calculation_date", nullable = false)
    private LocalDate calculationDate;

    public FinancialMetric() {}

    public FinancialMetric(CreateFinancialMetricCommand command, Bond bond) {
        this.bond = bond;
        this.tcea = command.tcea();
        this.trea = command.trea();
        this.duration = command.duration();
        this.modifiedDuration = command.modifiedDuration();
        this.convexity = command.convexity();
        this.marketPrice = command.marketPrice();
        this.calculationDate = command.calculationDate();
    }

    public FinancialMetric update(UpdateFinancialMetricCommand command) {
        this.tcea = command.tcea();
        this.trea = command.trea();
        this.duration = command.duration();
        this.modifiedDuration = command.modifiedDuration();
        this.convexity = command.convexity();
        this.marketPrice = command.marketPrice();
        this.calculationDate = command.calculationDate();
        return this;
    }
}