package com.stc.STCFilesBackend.service.dto;

import com.stc.STCFilesBackend.domain.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Objects;

public class ItemDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Long permission_group_id;

    private Long parent_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPermission_group_id() {
        return permission_group_id;
    }

    public void setPermission_group_id(Long permission_group_id) {
        this.permission_group_id = permission_group_id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemDTO)) {
            return false;
        }

        ItemDTO itemDTO = (ItemDTO) o;
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
        return "ItemDTO{" +
                "id=" + getId() +
                ", name=" + getName() + "'" +
                ", type='" + getType() + "'" +
                ", parent_id=" + getParent_id() +
                ", is_active='" + getIs_active() + "'" +
                "}";
    }
}
