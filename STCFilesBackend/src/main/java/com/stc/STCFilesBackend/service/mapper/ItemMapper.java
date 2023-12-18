package com.stc.STCFilesBackend.service.mapper;

import com.stc.STCFilesBackend.domain.Item;
import com.stc.STCFilesBackend.service.dto.ItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    ItemDTO toDto(Item item);
}
