package com.stc.STCFilesBackend.service.impl;

import com.stc.STCFilesBackend.domain.Permissions;
import com.stc.STCFilesBackend.repository.PermissionsRepository;
import com.stc.STCFilesBackend.service.PermissionsService;
import com.stc.STCFilesBackend.service.dto.PermissionsDTO;
import com.stc.STCFilesBackend.service.mapper.PermissionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionsImpl implements PermissionsService {

    private final Logger log = LoggerFactory.getLogger(PermissionsImpl.class);

    private final PermissionsRepository permissionsRepository;

    private final PermissionsMapper permissionsMapper;

    public PermissionsImpl(PermissionsRepository permissionsRepository, PermissionsMapper permissionsMapper) {
        this.permissionsRepository = permissionsRepository;
        this.permissionsMapper = permissionsMapper;
    }

    @Override
    public PermissionsDTO save(PermissionsDTO permissionsDTO) {
        log.debug("Request to save Permission : {}", permissionsDTO);
        Permissions permissions = permissionsMapper.toEntity(permissionsDTO);
        //
        permissions = permissionsRepository.save(permissions);
        return permissionsMapper.toDto(permissions);
    }

    @Override
    public Optional<PermissionsDTO> partialUpdate(PermissionsDTO permissionsDTO) {
        log.debug("Request to partially update Permission : {}", permissionsDTO);

        return permissionsRepository
                .findById(permissionsDTO.getId())
                .map(existingPermissions -> {
                    permissionsMapper.partialUpdate(existingPermissions, permissionsDTO);
                    return existingPermissions;
                })
                .map(permissionsRepository::save)
                .map(permissionsMapper::toDto);
    }

    @Override
    public List<PermissionsDTO> findAll() {
        List<Permissions> permissions = permissionsRepository.findAll();
        return permissions.stream().map(permissionsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Permissions");
        return permissionsRepository.findAll(pageable).map(permissionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionsDTO> findOne(Long id) {
        log.debug("Request to get Permissions : {}", id);
        return permissionsRepository.findById(id).map(permissionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permissions : {}", id);
        permissionsRepository.deleteById(id);
    }

    @Override
    public List<PermissionsDTO> findAllByPermissionGroup(Long permissionGroupId) {
        List<Permissions> result = permissionsRepository.findAllByGroup_Id(permissionGroupId);
        return result.stream().map(permissionsMapper::toDto).collect(Collectors.toList());
    }
}
