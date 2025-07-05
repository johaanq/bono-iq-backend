package com.bonoflow.api.profile.interfaces.rest;

import com.bonoflow.api.profile.domain.model.commands.DeleteInvestorCommand;
import com.bonoflow.api.profile.domain.model.queries.GetAllInvestorsQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetInvestorByUserIdQuery;
import com.bonoflow.api.profile.domain.services.InvestorCommandService;
import com.bonoflow.api.profile.domain.services.InvestorQueryService;
import com.bonoflow.api.profile.interfaces.rest.resources.InvestorResource;
import com.bonoflow.api.profile.interfaces.rest.transform.InvestorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="api/v1/investors", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Investors", description = "Investor Management Endpoints")
public class InvestorsController {
    private final InvestorCommandService investorCommandService;
    private final InvestorQueryService investorQueryService;

    public InvestorsController(InvestorCommandService investorCommandService, InvestorQueryService investorQueryService) {
        this.investorCommandService = investorCommandService;
        this.investorQueryService = investorQueryService;
    }

    @GetMapping
    public ResponseEntity<List<InvestorResource>> getAllInvestors() {
        var getAllInvestorsQuery = new GetAllInvestorsQuery();
        var investors = investorQueryService.handle(getAllInvestorsQuery);
        var investorResources = investors.stream().map(InvestorResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(investorResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorResource> getInvestorById(@PathVariable Long id) {
        var getInvestorByIdQuery = new GetInvestorByIdQuery(id);
        var investor = investorQueryService.handle(getInvestorByIdQuery);
        if (investor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var investorResource = InvestorResourceFromEntityAssembler.toResourceFromEntity(investor.get());
        return ResponseEntity.ok(investorResource);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<InvestorResource> getInvestorByUserId(@PathVariable Long userId) {
        var getInvestorByUserIdQuery = new GetInvestorByUserIdQuery(userId);
        var investor = investorQueryService.handle(getInvestorByUserIdQuery);
        if (investor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var investorResource = InvestorResourceFromEntityAssembler.toResourceFromEntity(investor.get());
        return ResponseEntity.ok(investorResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvestor(@PathVariable Long id) {
        var deleteInvestorCommand = new DeleteInvestorCommand(id);
        try {
            investorCommandService.handle(deleteInvestorCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
        return ResponseEntity.ok().body("Investor with id " + id + " deleted successfully");
    }
}