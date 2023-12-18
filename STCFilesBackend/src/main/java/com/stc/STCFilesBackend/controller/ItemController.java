package com.stc.STCFilesBackend.controller;

import com.stc.STCFilesBackend.controller.response.EntityId;
import com.stc.STCFilesBackend.controller.response.HeaderUtil;
import com.stc.STCFilesBackend.controller.response.ResponseUtil;
import com.stc.STCFilesBackend.exceptions.BadRequestAlertException;
import com.stc.STCFilesBackend.repository.ItemRepository;
import com.stc.STCFilesBackend.service.ItemService;
import com.stc.STCFilesBackend.service.dto.ItemDTO;
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
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    private static final String ENTITY_NAME = "item";
    private static final String applicationName = "STCFiles";

    private final ItemService itemService;

    private final ItemRepository itemRepository;

    public ItemController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @PostMapping(
            value = "/item",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityId<Long> createItem(@RequestBody ItemDTO itemDTO)
            throws URISyntaxException {
        log.debug("REST request to save Item : {}", itemDTO);
        if (itemDTO.getId() != null) {
            throw new BadRequestAlertException("A new Item cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemDTO result = itemService.save(itemDTO);

        EntityId<Long> entity = new EntityId<>();
        entity.setId(result.getId());
        return entity;
    }

    @PutMapping(
            value = "/item/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<ItemDTO> updateItem(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ItemDTO itemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update item : {}, {}", id, itemDTO);
        if (itemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemDTO result = itemService.save(itemDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(
            value = "/item/{id}",
            consumes = { "application/json", "application/merge-patch+json" },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<ItemDTO> partialUpdateItem(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody ItemDTO itemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Item : {}, {}", id, itemDTO);
        if (itemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemDTO result = itemService.save(itemDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping(
            value = "/item/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<ItemDTO> getItem(@PathVariable Long id) {
        log.debug("REST request to get Item : {}", id);
        Optional<ItemDTO> itemDTO = itemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemDTO);
    }

    @GetMapping(
            value = "/item-pageable",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Page<ItemDTO>> getItemPageable(Pageable pageable) {
        log.debug("REST request to get Item : {}", pageable);
        Page<ItemDTO> itemDTOS = itemService.findAll(pageable);
        return ResponseEntity.ok(itemDTOS);
    }

    @GetMapping(
            value = "/items",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<ItemDTO>> getItemAll() {
        log.debug("REST request to get Item : {}");
        List<ItemDTO> itemDTOS = itemService.findAll();
        return ResponseEntity.ok(itemDTOS);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete item : {}", id);
        itemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
