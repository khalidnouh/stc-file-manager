package com.stc.STCFilesBackend.service;

import com.stc.STCFilesBackend.service.dto.PermissionGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionGroupService {
    /**
     * Save a permission group.
     *
     * @param permissionGroupDTO the entity to save.
     * @return the persisted entity.
     */
    PermissionGroupDTO save(PermissionGroupDTO permissionGroupDTO);

    /**
     * Partially updates permission group.
     *
     * @param permissionGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PermissionGroupDTO> partialUpdate(PermissionGroupDTO permissionGroupDTO);

    /**
     * Get all permission groups.
     *
     * @return the list of entities.
     */
    List<PermissionGroupDTO> findAll();

    /**
     * Get all the permission groups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PermissionGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" permission group.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PermissionGroupDTO> findOne(Long id);

    /**
     * Delete the "id" permission group.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
