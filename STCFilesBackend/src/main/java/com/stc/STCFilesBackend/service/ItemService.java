package com.stc.STCFilesBackend.service;

import com.stc.STCFilesBackend.service.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    /**
     * Save a item.
     *
     * @param itemDTO the entity to save.
     * @return the persisted entity.
     */
    ItemDTO save(ItemDTO itemDTO);

    /**
     * Partially updates item.
     *
     * @param itemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemDTO> partialUpdate(ItemDTO itemDTO);

    /**
     * Get all items.
     *
     * @return the list of entities.
     */
    List<ItemDTO> findAll();

    /**
     * Get all the items.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" item.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemDTO> findOne(Long id);

    /**
     * Delete the "id" item.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * Save space item.
     *
     * @param itemDTO the entity to save.
     * @return the persisted entity.
     */
    ItemDTO saveSpaceItem(ItemDTO itemDTO) throws Exception;


    /**
     * Save item With Parent.
     *
     * @param itemDTO the entity to save.
     * @param parentDTO the parent Entity of Item.
     * @return the persisted entity.
     */
    ItemDTO saveItemWithParent(ItemDTO itemDTO, ItemDTO parentDTO) throws Exception;

}
