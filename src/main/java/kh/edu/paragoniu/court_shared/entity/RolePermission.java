package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RolePermissionId id = new RolePermissionId();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("systemRoleId")
    @JoinColumn(name = "system_role_id", nullable = false)
    private SystemRole systemRole;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("systemPermissionId")
    @JoinColumn(name = "system_permission_id", nullable = false)
    private SystemPermission systemPermission;
}
