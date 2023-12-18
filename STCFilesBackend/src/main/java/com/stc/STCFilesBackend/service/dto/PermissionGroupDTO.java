package com.stc.STCFilesBackend.service.dto;

import com.stc.STCFilesBackend.domain.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Objects;

public class PermissionGroupDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PermissionGroupDTO)) {
            return false;
        }

        PermissionGroupDTO itemDTO = (PermissionGroupDTO) o;
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
        return "PermissionGroupDTO{" +
                "id=" + getId() +
                ", group_name=" + getGroupName() + "'" +
                ", is_active='" + getIs_active() + "'" +
                "}";
    }
}
