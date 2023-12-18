package com.stc.STCFilesBackend.service.request;

import com.stc.STCFilesBackend.service.dto.ItemDTO;

import java.io.Serializable;

public class ParentItemRequest implements Serializable {

    ParentItemRequest() {

    }

    ItemDTO itemDTO;
    ItemDTO parentDTO;

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public ItemDTO getParentDTO() {
        return parentDTO;
    }

    public void setParentDTO(ItemDTO parentDTO) {
        this.parentDTO = parentDTO;
    }
}
