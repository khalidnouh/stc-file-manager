package com.stc.STCFilesBackend.service.dto;

import com.stc.STCFilesBackend.domain.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Objects;

public class PermissionsDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private String userEmail;

    private String permissionLevel;

    private Long permissionGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public Long getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(Long permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PermissionsDTO)) {
            return false;
        }

        PermissionsDTO itemDTO = (PermissionsDTO) o;
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
        return "PermissionsDTO{" +
                "id=" + getId() +
                ", user_email=" + getUserEmail() + "'" +
                ", permission_level=" + getPermissionLevel() + "'" +
                ", permission_group_id=" + getPermissionGroupId() +
                ", is_active='" + getIs_active() + "'" +
                "}";
    }
}
