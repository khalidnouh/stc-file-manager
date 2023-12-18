package com.stc.STCFilesBackend.service.request;

import com.stc.STCFilesBackend.service.dto.FilesDTO;
import com.stc.STCFilesBackend.service.dto.ItemDTO;

import java.io.Serializable;

public class ParentItemFileRequest implements Serializable {

    ParentItemFileRequest() {

    }

    ItemDTO itemDTO;
    ItemDTO parentDTO;

    FilesDTO filesDTO;

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

    public FilesDTO getFilesDTO() {
        return filesDTO;
    }

    public void setFilesDTO(FilesDTO filesDTO) {
        this.filesDTO = filesDTO;
    }
}
