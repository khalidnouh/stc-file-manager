package com.stc.STCFilesBackend.service.mapper;

import com.stc.STCFilesBackend.domain.PermissionGroup;
import com.stc.STCFilesBackend.service.dto.PermissionGroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PermissionGroupMapper extends EntityMapper<PermissionGroupDTO, PermissionGroup> {
    PermissionGroupDTO toDto(PermissionGroup permissionGroup);
}
