package com.bonoflow.api.profile.interfaces.rest;

import com.bonoflow.api.profile.domain.model.commands.DeleteClientCommand;
import com.bonoflow.api.profile.domain.model.queries.GetAllClientsQuery;
import com.bonoflow.api.profile.domain.model.queries.GetClientByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetClientByUserIdQuery;
import com.bonoflow.api.profile.domain.services.ClientCommandService;
import com.bonoflow.api.profile.domain.services.ClientQueryService;
import com.bonoflow.api.profile.interfaces.rest.resources.ClientResource;
import com.bonoflow.api.profile.interfaces.rest.transform.ClientResourceFromEntityAssembler;
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
@RequestMapping(value="api/v1/clients", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Clients", description = "Client Management Endpoints")
public class ClientsController {
    private final ClientCommandService ClientCommandService;
    private final ClientQueryService ClientQueryService;

    public ClientsController(ClientCommandService ClientCommandService, ClientQueryService ClientQueryService) {
        this.ClientCommandService = ClientCommandService;
        this.ClientQueryService = ClientQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResource>> getAllClients() {
        var getAllClientsQuery = new GetAllClientsQuery();
        var Clients = ClientQueryService.handle(getAllClientsQuery);
        var ClientResources = Clients.stream().map(ClientResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(ClientResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResource> getClientById(@PathVariable Long id) {
        var getClientByIdQuery = new GetClientByIdQuery(id);
        var Client = ClientQueryService.handle(getClientByIdQuery);
        if (Client.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var ClientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(Client.get());
        return ResponseEntity.ok(ClientResource);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ClientResource> getAdvisorByUserId(@PathVariable Long userId) {
        var getClientByUserIdQuery = new GetClientByUserIdQuery(userId);
        var Client = ClientQueryService.handle(getClientByUserIdQuery);
        if (Client.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var ClientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(Client.get());
        return ResponseEntity.ok(ClientResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        var deleteClientCommand = new DeleteClientCommand(id);
        try {
            ClientCommandService.handle(deleteClientCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
        return ResponseEntity.ok().body("Client with id " + id + " deleted successfully");
    }
}