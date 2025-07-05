package com.bonoflow.api.bond.interfaces.rest;

import com.bonoflow.api.bond.domain.model.commands.CreateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.DeleteInvestmentCommand;
import com.bonoflow.api.bond.domain.model.commands.UpdateInvestmentCommand;
import com.bonoflow.api.bond.domain.model.entities.Investment;
import com.bonoflow.api.bond.domain.model.queries.GetAllInvestmentsByInvestorIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllInvestmentsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetInvestmentByIdQuery;
import com.bonoflow.api.bond.domain.services.InvestmentCommandService;
import com.bonoflow.api.bond.domain.services.InvestmentQueryService;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateInvestmentResource;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateInvestmentResource;
import com.bonoflow.api.bond.interfaces.rest.resources.InvestmentResource;
import com.bonoflow.api.bond.interfaces.rest.transform.InvestmentResourceFromEntity;
import com.bonoflow.api.bond.interfaces.rest.transform.CreateInvestmentCommandFromResourceAssembler;
import com.bonoflow.api.bond.interfaces.rest.transform.UpdateInvestmentCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/investments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Investments", description = "Investment Management Endpoints")
public class InvestmentsController {
    private final InvestmentCommandService investmentCommandService;
    private final InvestmentQueryService investmentQueryService;

    public InvestmentsController(InvestmentCommandService investmentCommandService, InvestmentQueryService investmentQueryService) {
        this.investmentCommandService = investmentCommandService;
        this.investmentQueryService = investmentQueryService;
    }

    @GetMapping
    public ResponseEntity<List<InvestmentResource>> getAllInvestments() {
        var query = new GetAllInvestmentsQuery();
        var investments = investmentQueryService.handle(query);

        var investmentResources = investments.stream()
                .map(InvestmentResourceFromEntity::toResource)
                .toList();

        return ResponseEntity.ok(investmentResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentResource> getInvestmentById(@PathVariable Long id) {
        var query = new GetInvestmentByIdQuery(id);
        var investment = investmentQueryService.handle(query);
        if (investment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var investmentResource = InvestmentResourceFromEntity.toResource(investment.get());
        return ResponseEntity.ok(investmentResource);
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<InvestmentResource>> getAllInvestmentsByInvestorId(@PathVariable Long investorId) {
        var query = new GetAllInvestmentsByInvestorIdQuery(investorId);
        var investments = investmentQueryService.handle(query);

        var investmentResources = investments.stream()
                .map(InvestmentResourceFromEntity::toResource)
                .toList();

        return ResponseEntity.ok(investmentResources);
    }

    @PostMapping
    public ResponseEntity<InvestmentResource> createInvestment(@RequestBody CreateInvestmentResource createInvestmentResource) {
        var createInvestmentCommand = CreateInvestmentCommandFromResourceAssembler.toCommandFromResource(createInvestmentResource);
        Long investmentId;
        try {
            investmentId = investmentCommandService.handle(createInvestmentCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (investmentId == 0L) return ResponseEntity.badRequest().build();
        var investment = investmentQueryService.handle(new GetInvestmentByIdQuery(investmentId));
        if (investment.isEmpty()) return ResponseEntity.badRequest().build();
        var investmentResource = InvestmentResourceFromEntity.toResource(investment.get());
        return new ResponseEntity<>(investmentResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentResource> updateInvestment(@PathVariable Long id, @RequestBody UpdateInvestmentResource updateInvestmentResource) {
        var updateInvestmentCommand = UpdateInvestmentCommandFromResourceAssembler.toCommandFromResource(updateInvestmentResource, id);
        Optional<Investment> investment;
        try {
            investment = investmentCommandService.handle(updateInvestmentCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (investment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var investmentResource = InvestmentResourceFromEntity.toResource(investment.get());
        return ResponseEntity.ok(investmentResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvestment(@PathVariable Long id) {
        try {
            investmentCommandService.handle(new DeleteInvestmentCommand(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok("Investment with ID " + id + " deleted successfully");
    }
}