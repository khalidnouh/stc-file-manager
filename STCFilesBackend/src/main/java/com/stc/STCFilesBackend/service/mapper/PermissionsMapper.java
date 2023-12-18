package com.stc.STCFilesBackend.service.mapper;

import com.stc.STCFilesBackend.domain.Permissions;
import com.stc.STCFilesBackend.service.dto.PermissionsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PermissionsMapper extends EntityMapper<PermissionsDTO, Permissions> {
    PermissionsDTO toDto(Permissions permissions);
}
