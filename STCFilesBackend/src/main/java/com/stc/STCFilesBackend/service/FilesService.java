package com.stc.STCFilesBackend.service;

import com.stc.STCFilesBackend.service.dto.FilesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

public interface FilesService {
    /**
     * Save a file.
     *
     * @param filesDTO the entity to save.
     * @return the persisted entity.
     */
    FilesDTO save(FilesDTO filesDTO);

    /**
     * Partially updates a files.
     *
     * @param filesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FilesDTO> partialUpdate(FilesDTO filesDTO);

    /**
     * Get all the files.
     *
     * @return the list of entities.
     */
    List<FilesDTO> findAll();

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FilesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" file.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FilesDTO> findOne(Long id);

    /**
     * Delete the "id" file.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Download file.
     *
     * @param filesDTO the entity to save.
     * @return the persisted entity.
     */
    FileOutputStream downloadFile(FilesDTO filesDTO);
}
