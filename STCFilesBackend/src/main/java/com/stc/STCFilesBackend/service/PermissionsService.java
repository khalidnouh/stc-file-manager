package com.stc.STCFilesBackend.service;

import com.stc.STCFilesBackend.service.dto.PermissionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermissionsService {
    /**
     * Save a permissions.
     *
     * @param permissionsDTO the entity to save.
     * @return the persisted entity.
     */
    PermissionsDTO save(PermissionsDTO permissionsDTO);

    /**
     * Partially updates permissions.
     *
     * @param permissionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PermissionsDTO> partialUpdate(PermissionsDTO permissionsDTO);

    /**
     * Get all permissions.
     *
     * @return the list of entities.
     */
    List<PermissionsDTO> findAll();

    /**
     * Get all the permissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PermissionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" permission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PermissionsDTO> findOne(Long id);

    /**
     * Delete the "id" permission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * Get all permissions By Specific Permission Group.
     *
     * @return the list of entities.
     */
    List<PermissionsDTO> findAllByPermissionGroup(Long permissionGroupId);
}
