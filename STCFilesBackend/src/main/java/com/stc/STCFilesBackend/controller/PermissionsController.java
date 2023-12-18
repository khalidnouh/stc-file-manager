package com.stc.STCFilesBackend.controller;

import com.stc.STCFilesBackend.controller.response.EntityId;
import com.stc.STCFilesBackend.controller.response.HeaderUtil;
import com.stc.STCFilesBackend.controller.response.ResponseUtil;
import com.stc.STCFilesBackend.exceptions.BadRequestAlertException;
import com.stc.STCFilesBackend.repository.PermissionsRepository;
import com.stc.STCFilesBackend.service.PermissionsService;
import com.stc.STCFilesBackend.service.dto.PermissionsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
public class PermissionsController {

    private final Logger log = LoggerFactory.getLogger(PermissionsController.class);

    private static final String ENTITY_NAME = "permissions";
    private static final String applicationName = "STCFiles";

    private final PermissionsService permissionsService;

    private final PermissionsRepository permissionsRepository;

    public PermissionsController(PermissionsService permissionsService, PermissionsRepository permissionsRepository) {
        this.permissionsService = permissionsService;
        this.permissionsRepository = permissionsRepository;
    }

    @PostMapping(
            value = "/permissions",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createPermissions(@RequestBody PermissionsDTO permissionsDTO)
            throws URISyntaxException {
        log.debug("REST request to save Permissions : {}", permissionsDTO);
        if (permissionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new Permissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermissionsDTO result = permissionsService.save(permissionsDTO);

        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @PutMapping(
            value = "/permissions/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<PermissionsDTO> updatePermissions(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody PermissionsDTO permissionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update permissions : {}, {}", id, permissionsDTO);
        if (permissionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permissionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PermissionsDTO result = permissionsService.save(permissionsDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(
            value = "/permissions/{id}",
            consumes = { "application/json", "application/merge-patch+json" },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<PermissionsDTO> partialUpdatePermissions(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody PermissionsDTO permissionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Permissions : {}, {}", id, permissionsDTO);
        if (permissionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permissionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PermissionsDTO result = permissionsService.save(permissionsDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping(
            value = "/permissions/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<PermissionsDTO> getPermissions(@PathVariable Long id) {
        log.debug("REST request to get Permissions : {}", id);
        Optional<PermissionsDTO> permissionsDTO = permissionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permissionsDTO);
    }

    @GetMapping(
            value = "/permissions-pageable",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Page<PermissionsDTO>> getPermissionsPageable(Pageable pageable) {
        log.debug("REST request to get Permissions : {}", pageable);
        Page<PermissionsDTO> permissionsDTOS = permissionsService.findAll(pageable);
        return ResponseEntity.ok(permissionsDTOS);
    }

    @GetMapping(
            value = "/permissions",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<PermissionsDTO>> getPermissionsAll() {
        log.debug("REST request to get Permissions : {}");
        List<PermissionsDTO> permissionsDTOS = permissionsService.findAll();
        return ResponseEntity.ok(permissionsDTOS);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermissions(@PathVariable Long id) {
        log.debug("REST request to delete Permissions : {}", id);
        permissionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
