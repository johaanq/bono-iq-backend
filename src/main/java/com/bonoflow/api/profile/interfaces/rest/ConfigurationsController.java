package com.bonoflow.api.profile.interfaces.rest;

import com.bonoflow.api.profile.domain.model.entities.Configuration;
import com.bonoflow.api.profile.domain.model.queries.GetConfigurationByIdQuery;
import com.bonoflow.api.profile.domain.model.queries.GetConfigurationByUserIdQuery;
import com.bonoflow.api.profile.domain.services.ConfigurationCommandService;
import com.bonoflow.api.profile.domain.services.ConfigurationQueryService;
import com.bonoflow.api.profile.interfaces.rest.resources.ConfigurationResource;
import com.bonoflow.api.profile.interfaces.rest.resources.UpdateConfigurationResource;
import com.bonoflow.api.profile.interfaces.rest.transform.ConfigurationResourceFromEntityAssembler;
import com.bonoflow.api.profile.interfaces.rest.transform.UpdateConfigurationCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="api/v1/configurations", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Configuration", description = "Configuration Client Management Endpoints")
public class ConfigurationsController {

    private final ConfigurationCommandService configurationCommandService;
    private final ConfigurationQueryService configurationQueryService;

    public ConfigurationsController(ConfigurationCommandService configurationCommandService,
                                    ConfigurationQueryService configurationQueryService) {
        this.configurationCommandService = configurationCommandService;
        this.configurationQueryService = configurationQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationResource> getConfigurationById(@PathVariable Long id) {
        var configuration = configurationQueryService.handle(new GetConfigurationByIdQuery(id));
        if (configuration.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = ConfigurationResourceFromEntityAssembler.toResource(configuration.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ConfigurationResource> getConfigurationByUserId(@PathVariable Long userId) {
        var query = new GetConfigurationByUserIdQuery(userId);
        Optional<Configuration> configuration = configurationQueryService.handle(query);
        if (configuration.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = ConfigurationResourceFromEntityAssembler.toResource(configuration.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationResource> updateConfiguration(@PathVariable Long id, @RequestBody UpdateConfigurationResource resource) {
        var updateCommand = UpdateConfigurationCommandFromResourceAssembler.toCommand(id, resource);
        var updatedConfiguration = configurationCommandService.handle(updateCommand);
        if (updatedConfiguration.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var configurationResource = ConfigurationResourceFromEntityAssembler.toResource(updatedConfiguration.get());
        return ResponseEntity.ok(configurationResource);
    }

}