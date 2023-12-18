package com.stc.STCFilesBackend.service.impl;

import com.stc.STCFilesBackend.domain.PermissionGroup;
import com.stc.STCFilesBackend.repository.PermissionGroupRepository;
import com.stc.STCFilesBackend.service.PermissionGroupService;
import com.stc.STCFilesBackend.service.dto.PermissionGroupDTO;
import com.stc.STCFilesBackend.service.mapper.PermissionGroupMapper;
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
public class PermissionGroupImpl implements PermissionGroupService {

    private final Logger log = LoggerFactory.getLogger(PermissionGroupImpl.class);

    private final PermissionGroupRepository permissionGroupRepository;

    private final PermissionGroupMapper permissionGroupMapper;

    public PermissionGroupImpl(PermissionGroupRepository permissionGroupRepository, PermissionGroupMapper permissionGroupMapper) {
        this.permissionGroupRepository = permissionGroupRepository;
        this.permissionGroupMapper = permissionGroupMapper;
    }

    @Override
    public PermissionGroupDTO save(PermissionGroupDTO permissionGroupDTO) {
        log.debug("Request to save Permission Group : {}", permissionGroupDTO);
        PermissionGroup permissionGroup = permissionGroupMapper.toEntity(permissionGroupDTO);
        //
        permissionGroup = permissionGroupRepository.save(permissionGroup);
        return permissionGroupMapper.toDto(permissionGroup);
    }

    @Override
    public Optional<PermissionGroupDTO> partialUpdate(PermissionGroupDTO permissionGroupDTO) {
        log.debug("Request to partially update Permission Group : {}", permissionGroupDTO);

        return permissionGroupRepository
                .findById(permissionGroupDTO.getId())
                .map(existingPermissions -> {
                    permissionGroupMapper.partialUpdate(existingPermissions, permissionGroupDTO);
                    return existingPermissions;
                })
                .map(permissionGroupRepository::save)
                .map(permissionGroupMapper::toDto);
    }

    @Override
    public List<PermissionGroupDTO> findAll() {
        List<PermissionGroup> permissionGroups = permissionGroupRepository.findAll();
        return permissionGroups.stream().map(permissionGroupMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Permission Groups");
        return permissionGroupRepository.findAll(pageable).map(permissionGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionGroupDTO> findOne(Long id) {
        log.debug("Request to get Permission Groups : {}", id);
        return permissionGroupRepository.findById(id).map(permissionGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permission Group : {}", id);
        permissionGroupRepository.deleteById(id);
    }
}
