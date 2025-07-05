package com.bonoflow.api.bond.interfaces.rest.transform;

import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import com.bonoflow.api.bond.interfaces.rest.resources.FinancialMetricResource;

public class FinancialMetricResourceFromEntity {
    public static FinancialMetricResource toResource(FinancialMetric financialMetric) {
        return new FinancialMetricResource(
                financialMetric.getId(),
                financialMetric.getBond().getId(),
                financialMetric.getTcea(),
                financialMetric.getTrea(),
                financialMetric.getDuration(),
                financialMetric.getModifiedDuration(),
                financialMetric.getConvexity(),
                financialMetric.getMarketPrice(),
                financialMetric.getCalculationDate()
        );
    }
}