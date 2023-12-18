package com.stc.STCFilesBackend.service.dto;

import com.stc.STCFilesBackend.domain.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Objects;

public class FilesDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private byte[] binaryFile;

    private Long item_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(byte[] binaryFile) {
        this.binaryFile = binaryFile;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilesDTO)) {
            return false;
        }

        FilesDTO itemDTO = (FilesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "FilesDTO{" +
                "id=" + getId() +
                ", binary_file=" + getBinaryFile() +
                ", item_id='" + getItem_id() +
                ", is_active='" + getIs_active() + "'" +
                "}";
    }
}
