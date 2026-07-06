package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "system_role_id", nullable = false)
    private Integer systemRoleId;

    @Column(name = "system_permission_id", nullable = false)
    private Integer systemPermissionId;
}
