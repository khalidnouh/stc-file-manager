package com.stc.STCFilesBackend.domain;

import com.stc.STCFilesBackend.exceptions.BadRequestAlertException;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item")
@Setter
@Getter
@EnableJpaAuditing
public class Item extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", columnDefinition = "nvarchar2(256)")
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private PermissionGroup permissionGroup;

    @NotNull
    @Column(name = "type", columnDefinition = "char(2)")
    private String type;

    @Column(name = "parent_id", columnDefinition = "number")
    private Long parentId;
    /**
     * To Check id of object equals to current object or instance of it
     *
     * return boolean value
     * Example:
     * It is reflexive: for any non-null reference value x, x.equals(x) should return true.
     * It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
     * It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
     * It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
     * For any non-null reference value x, x.equals(null) should return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        if (getId() == null || getId() <= 0) {
            throw new BadRequestAlertException("idNotExists", "Item", "notFound");
        }
        return "Item{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", type='" + getType() + "'" +
                ", parent_id=" + getParentId() +
                ", permission_group_id='" + getPermissionGroup().getId() + "'" +
                ", is_active='" + getIs_active() + "'" +
                "}";
    }
}
