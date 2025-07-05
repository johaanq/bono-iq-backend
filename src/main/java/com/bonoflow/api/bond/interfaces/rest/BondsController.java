package com.bonoflow.api.bond.interfaces.rest;

import com.bonoflow.api.bond.domain.model.aggregates.Bond;
import com.bonoflow.api.bond.domain.model.commands.DeleteBondCommand;
import com.bonoflow.api.bond.domain.model.queries.GetAllBondsByClientIdQuery;
import com.bonoflow.api.bond.domain.model.queries.GetAllBondsQuery;
import com.bonoflow.api.bond.domain.model.queries.GetBondByIdQuery;
import com.bonoflow.api.bond.domain.services.BondCommandService;
import com.bonoflow.api.bond.domain.services.BondQueryService;
import com.bonoflow.api.bond.interfaces.rest.resources.BondResource;
import com.bonoflow.api.bond.interfaces.rest.resources.CreateBondResource;
import com.bonoflow.api.bond.interfaces.rest.resources.UpdateBondResource;
import com.bonoflow.api.bond.interfaces.rest.transform.BondResourceFromEntity;
import com.bonoflow.api.bond.interfaces.rest.transform.CreateBondCommandFromResourceAssembler;
import com.bonoflow.api.bond.interfaces.rest.transform.UpdateBondCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/bonds", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Bonds", description = "Bond Management Endpoints")
public class BondsController {
    private final BondCommandService bondCommandService;
    private final BondQueryService bondQueryService;

    public BondsController(BondCommandService bondCommandService, BondQueryService bondQueryService) {
        this.bondCommandService = bondCommandService;
        this.bondQueryService = bondQueryService;
    }

    @GetMapping
    public ResponseEntity<List<BondResource>> getAllBonds() {
        var query = new GetAllBondsQuery();
        var bonds = bondQueryService.handle(query);

        var bondResources = bonds.stream()
                .map(BondResourceFromEntity::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(bondResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BondResource> getBondById(@PathVariable Long id) {
        var query = new GetBondByIdQuery(id);
        var bond = bondQueryService.handle(query);
        if (bond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondResource = BondResourceFromEntity.toResourceFromEntity(bond.get());
        return ResponseEntity.ok(bondResource);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BondResource>> getBondsByClientId(@PathVariable Long clientId) {
        var query = new GetAllBondsByClientIdQuery(clientId);
        var bonds = bondQueryService.handle(query);

        var bondResources = bonds.stream()
                .map(BondResourceFromEntity::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(bondResources);
    }

    @PostMapping
    public ResponseEntity<BondResource> createBond(@RequestBody CreateBondResource createBondResource) {
        var command = CreateBondCommandFromResourceAssembler.toCommandFromResource(createBondResource);
        Long bondId;
        try {
            bondId = bondCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (bondId == 0L) return ResponseEntity.badRequest().build();
        var bond = bondQueryService.handle(new GetBondByIdQuery(bondId));
        if (bond.isEmpty()) return ResponseEntity.badRequest().build();
        var bondResource = BondResourceFromEntity.toResourceFromEntity(bond.get());
        return new ResponseEntity<>(bondResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BondResource> updateBond(@PathVariable Long id, @RequestBody UpdateBondResource updateBondResource) {
        var command = UpdateBondCommandFromResourceAssembler.toCommandFromResource(updateBondResource, id);
        Optional<Bond> bond;
        try {
            bond = bondCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (bond.isEmpty()) return ResponseEntity.notFound().build();
        var bondResource = BondResourceFromEntity.toResourceFromEntity(bond.get());
        return ResponseEntity.ok(bondResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBond(@PathVariable Long id) {
        try {
            bondCommandService.handle(new DeleteBondCommand(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
        return ResponseEntity.ok().body("Bond with id " + id + " deleted successfully");
    }
}