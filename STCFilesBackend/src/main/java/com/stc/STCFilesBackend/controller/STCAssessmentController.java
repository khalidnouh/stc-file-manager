package com.stc.STCFilesBackend.controller;

import com.stc.STCFilesBackend.controller.response.EntityId;
import com.stc.STCFilesBackend.exceptions.BadRequestAlertException;
import com.stc.STCFilesBackend.service.FilesService;
import com.stc.STCFilesBackend.service.ItemService;
import com.stc.STCFilesBackend.service.dto.FilesDTO;
import com.stc.STCFilesBackend.service.dto.ItemDTO;
import com.stc.STCFilesBackend.service.request.ParentItemFileRequest;
import com.stc.STCFilesBackend.service.request.ParentItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/v1/api")
public class STCAssessmentController {

    private final Logger log = LoggerFactory.getLogger(STCAssessmentController.class);

    private static final String ENTITY_NAME = "STC";
    private static final String applicationName = "STCFiles";

    private final ItemService itemService;

    private final FilesService filesService;

    public STCAssessmentController(ItemService itemService, FilesService filesService) {
        this.itemService = itemService;
        this.filesService = filesService;
    }

    @PostMapping(
            value = "/stc-assessment-space",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createSpace(@RequestBody ItemDTO itemDTO)
            throws Exception {
        log.debug("REST request to save space item : {}", itemDTO);
        if (itemDTO.getId() != null) {
            throw new BadRequestAlertException("A new Space Item cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemDTO result = itemService.saveSpaceItem(itemDTO);

        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @PostMapping(
            value = "/stc-assessment-folder",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createFolder(@RequestBody ParentItemRequest parentItemRequest)
            throws Exception {
        log.debug("REST request to save folder item : {}", parentItemRequest);
        ItemDTO result = itemService.saveItemWithParent(parentItemRequest.getItemDTO(), parentItemRequest.getParentDTO());

        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @PostMapping(
            value = "/stc-assessment-file",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createFolderFiles(@RequestBody ParentItemFileRequest parentItemFileRequest)
            throws Exception {
        log.debug("REST request to save folder item : {}", parentItemFileRequest);
        ItemDTO result = itemService.saveItemWithParent(parentItemFileRequest.getItemDTO(), parentItemFileRequest.getParentDTO());
        filesService.save(parentItemFileRequest.getFilesDTO());
        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @GetMapping(
            value = "/download-file",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public String downloadFile(@RequestBody FilesDTO filesDTO) throws Exception {
        byte[] fileContent = filesDTO.getBinaryFile();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }
}
