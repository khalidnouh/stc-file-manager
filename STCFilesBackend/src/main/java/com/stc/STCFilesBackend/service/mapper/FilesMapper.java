package com.stc.STCFilesBackend.service.mapper;

import com.stc.STCFilesBackend.domain.Files;
import com.stc.STCFilesBackend.service.dto.FilesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FilesMapper extends EntityMapper<FilesDTO, Files> {
    FilesDTO toDto(Files files);
}
