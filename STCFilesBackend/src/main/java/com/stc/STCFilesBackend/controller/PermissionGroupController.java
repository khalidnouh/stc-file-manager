package com.stc.STCFilesBackend.controller;

import com.stc.STCFilesBackend.controller.response.EntityId;
import com.stc.STCFilesBackend.controller.response.HeaderUtil;
import com.stc.STCFilesBackend.controller.response.ResponseUtil;
import com.stc.STCFilesBackend.exceptions.BadRequestAlertException;
import com.stc.STCFilesBackend.repository.PermissionGroupRepository;
import com.stc.STCFilesBackend.service.PermissionGroupService;
import com.stc.STCFilesBackend.service.dto.PermissionGroupDTO;
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
public class PermissionGroupController {

    private final Logger log = LoggerFactory.getLogger(PermissionGroupController.class);

    private static final String ENTITY_NAME = "permission_group";
    private static final String applicationName = "STCFiles";

    private final PermissionGroupService permissionGroupService;

    private final PermissionGroupRepository permissionGroupRepository;

    public PermissionGroupController(PermissionGroupService permissionGroupService, PermissionGroupRepository permissionGroupRepository) {
        this.permissionGroupService = permissionGroupService;
        this.permissionGroupRepository = permissionGroupRepository;
    }

    @PostMapping(
            value = "/permission-group",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createPermissionGroup(@RequestBody PermissionGroupDTO permissionGroupDTO)
            throws URISyntaxException {
        log.debug("REST request to save Permission Group : {}", permissionGroupDTO);
        if (permissionGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new Permission Group cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermissionGroupDTO result = permissionGroupService.save(permissionGroupDTO);

        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @PutMapping(
            value = "/permission-group/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<PermissionGroupDTO> updatePermissionGroup(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody PermissionGroupDTO permissionGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update permission group : {}, {}", id, permissionGroupDTO);
        if (permissionGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permissionGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permissionGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PermissionGroupDTO result = permissionGroupService.save(permissionGroupDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(
            value = "/permission-group/{id}",
            consumes = { "application/json", "application/merge-patch+json" },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<PermissionGroupDTO> partialUpdatePermissionGroup(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody PermissionGroupDTO permissionGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Permission Group : {}, {}", id, permissionGroupDTO);
        if (permissionGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permissionGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permissionGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PermissionGroupDTO result = permissionGroupService.save(permissionGroupDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping(
            value = "/permission-group/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<PermissionGroupDTO> getPermissionGroup(@PathVariable Long id) {
        log.debug("REST request to get Permission Group : {}", id);
        Optional<PermissionGroupDTO> permissionGroupDTO = permissionGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permissionGroupDTO);
    }

    @GetMapping(
            value = "/permission-group-pageable",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Page<PermissionGroupDTO>> getPermissionGroupPageable(Pageable pageable) {
        log.debug("REST request to get Permission Group : {}", pageable);
        Page<PermissionGroupDTO> permissionGroupDTOS = permissionGroupService.findAll(pageable);
        return ResponseEntity.ok(permissionGroupDTOS);
    }

    @GetMapping(
            value = "/permission-groups",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<PermissionGroupDTO>> getPermissionGroupAll() {
        log.debug("REST request to get Permission Group : {}");
        List<PermissionGroupDTO> permissionGroupDTOS = permissionGroupService.findAll();
        return ResponseEntity.ok(permissionGroupDTOS);
    }

    @DeleteMapping("/permission-group/{id}")
    public ResponseEntity<Void> deletePermissionGroup(@PathVariable Long id) {
        log.debug("REST request to delete permission group : {}", id);
        permissionGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
