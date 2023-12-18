package com.stc.STCFilesBackend.controller;

import com.stc.STCFilesBackend.controller.response.EntityId;
import com.stc.STCFilesBackend.controller.response.HeaderUtil;
import com.stc.STCFilesBackend.controller.response.ResponseUtil;
import com.stc.STCFilesBackend.exceptions.BadRequestAlertException;
import com.stc.STCFilesBackend.repository.FilesRepository;
import com.stc.STCFilesBackend.service.FilesService;
import com.stc.STCFilesBackend.service.dto.FilesDTO;
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
public class FilesController {

    private final Logger log = LoggerFactory.getLogger(FilesController.class);

    private static final String ENTITY_NAME = "files";
    private static final String applicationName = "STCFiles";

    private final FilesService filesService;

    private final FilesRepository filesRepository;

    public FilesController(FilesService filesService, FilesRepository filesRepository) {
        this.filesService = filesService;
        this.filesRepository = filesRepository;
    }

    @PostMapping(
            value = "/files",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createFiles(@RequestBody FilesDTO filesDTO)
            throws URISyntaxException {
        log.debug("REST request to save Files : {}", filesDTO);
        if (filesDTO.getId() != null) {
            throw new BadRequestAlertException("A new File cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilesDTO result = filesService.save(filesDTO);

        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @PutMapping(
            value = "/files/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FilesDTO> updateFiles(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody FilesDTO filesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update File : {}, {}", id, filesDTO);
        if (filesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FilesDTO result = filesService.save(filesDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(
            value = "/files/{id}",
            consumes = { "application/json", "application/merge-patch+json" },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FilesDTO> partialUpdateFiles(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody FilesDTO filesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update File : {}, {}", id, filesDTO);
        if (filesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FilesDTO result = filesService.save(filesDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping(
            value = "/files/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<FilesDTO> getFiles(@PathVariable Long id) {
        log.debug("REST request to get File : {}", id);
        Optional<FilesDTO> filesDTO = filesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filesDTO);
    }

    @GetMapping(
            value = "/file-pageable",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Page<FilesDTO>> getFilePageable(Pageable pageable) {
        log.debug("REST request to get File : {}", pageable);
        Page<FilesDTO> filesDTOS = filesService.findAll(pageable);
        return ResponseEntity.ok(filesDTOS);
    }

    @GetMapping(
            value = "/files",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<FilesDTO>> getFilesAll() {
        log.debug("REST request to get Files : {}");
        List<FilesDTO> filesDTOS = filesService.findAll();
        return ResponseEntity.ok(filesDTOS);
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFiles(@PathVariable Long id) {
        log.debug("REST request to delete files : {}", id);
        filesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
