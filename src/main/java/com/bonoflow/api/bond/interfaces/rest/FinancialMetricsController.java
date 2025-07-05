package com.bonoflow.api.bond.interfaces.rest;

import com.bonoflow.api.bond.domain.model.entities.FinancialMetric;
import com.bonoflow.api.bond.domain.model.commands.DeleteFinancialMetricCommand;
import com.bonoflow.api.bond.domain.model.queries.GetAllFinancialMetricsByBondIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllFinancialMetricsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetFinancialMetricByBondIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetFinancialMetricByIdQuery;
import com.bonoflow.api.bond.domain.services.FinancialMetricCommandService;
import com.bonoflow.api.bond.domain.services.FinancialMetricQueryService;
import com.bonoflow.api.bond.interfaces.rest.resources.FinancialMetricResource;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateFinancialMetricResource;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateFinancialMetricResource;
import com.bonoflow.api.bond.interfaces.rest.transform.FinancialMetricResourceFromEntity;
import com.bonoflow.api.bond.interfaces.rest.transform.CreateFinancialMetricCommandFromResourceAssembler;
import com.bonoflow.api.bond.interfaces.rest.transform.UpdateFinancialMetricCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/v1/financial-metrics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Financial Metrics", description = "Financial Metric Management Endpoints")
public class FinancialMetricsController {
    private final FinancialMetricCommandService financialMetricCommandService;
    private final FinancialMetricQueryService financialMetricQueryService;

    public FinancialMetricsController(FinancialMetricCommandService financialMetricCommandService, FinancialMetricQueryService financialMetricQueryService) {
        this.financialMetricCommandService = financialMetricCommandService;
        this.financialMetricQueryService = financialMetricQueryService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialMetricResource>> getAllFinancialMetrics() {
        var query = new GetAllFinancialMetricsQuery();
        var financialMetrics = financialMetricQueryService.handle(query);

        var financialMetricResources = financialMetrics.stream()
                .map(FinancialMetricResourceFromEntity::toResource)
                .toList();

        return ResponseEntity.ok(financialMetricResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialMetricResource> getFinancialMetricById(@PathVariable Long id) {
        var query = new GetFinancialMetricByIdQuery(id);
        var financialMetric = financialMetricQueryService.handle(query);
        if (financialMetric.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var financialMetricResource = FinancialMetricResourceFromEntity.toResource(financialMetric.get());
        return ResponseEntity.ok(financialMetricResource);
    }

    @GetMapping("bond/{bondId}")
    public ResponseEntity<FinancialMetricResource> getFinancialMetricByBondId(@PathVariable Long bondId) {
        var query = new GetFinancialMetricByBondIdQuery(bondId);
        var financialMetric = financialMetricQueryService.handle(query);
        if (financialMetric.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var financialMetricResource = FinancialMetricResourceFromEntity.toResource(financialMetric.get());
        return ResponseEntity.ok(financialMetricResource);
    }

    @PostMapping
    public ResponseEntity<FinancialMetricResource> createFinancialMetric(@RequestBody CreateFinancialMetricResource createFinancialMetricResource) {
        var command = CreateFinancialMetricCommandFromResourceAssembler.toCommand(createFinancialMetricResource);
        Long financialMetricId;
        try {
            financialMetricId = financialMetricCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (financialMetricId == 0L) return ResponseEntity.badRequest().build();
        var financialMetric = financialMetricQueryService.handle(new GetFinancialMetricByIdQuery(financialMetricId));
        if (financialMetric.isEmpty()) return ResponseEntity.badRequest().build();
        var financialMetricResource = FinancialMetricResourceFromEntity.toResource(financialMetric.get());
        return new ResponseEntity<>(financialMetricResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialMetricResource> updateFinancialMetric(@PathVariable Long id, @RequestBody UpdateFinancialMetricResource updateFinancialMetricResource) {
        var command = UpdateFinancialMetricCommandFromResourceAssembler.toCommand(updateFinancialMetricResource, id);
        Optional<FinancialMetric> financialMetric;
        try {
            financialMetric = financialMetricCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (financialMetric.isEmpty()) return ResponseEntity.notFound().build();
        var financialMetricResource = FinancialMetricResourceFromEntity.toResource(financialMetric.get());
        return ResponseEntity.ok(financialMetricResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFinancialMetric(@PathVariable Long id) {
        try {
            financialMetricCommandService.handle(new DeleteFinancialMetricCommand(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
        return ResponseEntity.ok().body("Financial Metric with id " + id + " deleted successfully");
    }
}