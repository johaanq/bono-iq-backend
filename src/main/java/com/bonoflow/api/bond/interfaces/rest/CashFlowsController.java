package com.bonoflow.api.bond.interfaces.rest;

import com.bonoflow.api.bond.domain.model.commands.CreateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteCashFlowCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateCashFlowCommand;
import com.bonoflow.api.bond.domain.model.entities.CashFlow;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsByBondId;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsByClientIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllCashFlowsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetCashFlowByIdQuery;
import com.bonoflow.api.bond.domain.services.CashFlowCommandService;
import com.bonoflow.api.bond.domain.services.CashFlowQueryService;
import com.bonoflow.api.bond.interfaces.rest.resources.CashFlowResource;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateCashFlowResource;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateCashFlowResource;
import com.bonoflow.api.bond.interfaces.rest.transform.CashFlowResourceFromEntity;
import com.bonoflow.api.bond.interfaces.rest.transform.CreateCashFlowCommandFromResourceAssembler;
import com.bonoflow.api.bond.interfaces.rest.transform.UpdateCashFlowCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/cashflows", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CashFlows", description = "Cash Flow Management Endpoints")
public class CashFlowsController {
    private final CashFlowCommandService cashFlowCommandService;
    private final CashFlowQueryService cashFlowQueryService;

    public CashFlowsController(CashFlowCommandService cashFlowCommandService, CashFlowQueryService cashFlowQueryService) {
        this.cashFlowCommandService = cashFlowCommandService;
        this.cashFlowQueryService = cashFlowQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CashFlowResource>> getAllCashFlows() {
        var query = new GetAllCashFlowsQuery();
        var cashFlows = cashFlowQueryService.handle(query);

        var cashFlowResources = cashFlows.stream()
                .map(CashFlowResourceFromEntity::toResource)
                .toList();

        return ResponseEntity.ok(cashFlowResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashFlowResource> getCashFlowById(@PathVariable Long id) {
        var query = new GetCashFlowByIdQuery(id);
        var cashFlow = cashFlowQueryService.handle(query);
        if (cashFlow.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cashFlowResource = CashFlowResourceFromEntity.toResource(cashFlow.get());
        return ResponseEntity.ok(cashFlowResource);
    }

    @GetMapping("/bond/{bondId}")
    public ResponseEntity<List<CashFlowResource>> getAllCashFlowsByBondId(@PathVariable Long bondId) {
        var query = new GetAllCashFlowsByBondId(bondId);
        var cashFlows = cashFlowQueryService.handle(query);

        var cashFlowResources = cashFlows.stream()
                .map(CashFlowResourceFromEntity::toResource)
                .toList();

        return ResponseEntity.ok(cashFlowResources);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CashFlowResource>> getAllCashFlowsByClientId(@PathVariable Long clientId) {
        var query = new GetAllCashFlowsByClientIdQuery(clientId);
        var cashFlows = cashFlowQueryService.handle(query);

        var cashFlowResources = cashFlows.stream()
                .map(CashFlowResourceFromEntity::toResource)
                .toList();

        return ResponseEntity.ok(cashFlowResources);
    }

    @PostMapping
    public ResponseEntity<CashFlowResource> createCashFlow(@RequestBody CreateCashFlowResource createCashFlowResource) {
        var createCashFlowCommand = CreateCashFlowCommandFromResourceAssembler.toCommandFromResource(createCashFlowResource);
        Long cashFlowId;
        try {
            cashFlowId = cashFlowCommandService.handle(createCashFlowCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (cashFlowId == 0L) return ResponseEntity.badRequest().build();
        var cashFlow = cashFlowQueryService.handle(new GetCashFlowByIdQuery(cashFlowId));
        if (cashFlow.isEmpty()) return ResponseEntity.badRequest().build();
        var cashFlowResource = CashFlowResourceFromEntity.toResource(cashFlow.get());
        return new ResponseEntity<>(cashFlowResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashFlowResource> updateCashFlow(@PathVariable Long id, @RequestBody UpdateCashFlowResource updateCashFlowResource) {
        var updateCashFlowCommand = UpdateCashFlowCommandFromResourceAssembler.toCommandFromResource(updateCashFlowResource, id);
        Optional<CashFlow> cashFlow;
        try {
            cashFlow = cashFlowCommandService.handle(updateCashFlowCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (cashFlow.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cashFlowResource = CashFlowResourceFromEntity.toResource(cashFlow.get());
        return ResponseEntity.ok(cashFlowResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCashFlow(@PathVariable Long id) {
        try {
            cashFlowCommandService.handle(new DeleteCashFlowCommand(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok("CashFlow with ID " + id + " deleted successfully");
    }
}