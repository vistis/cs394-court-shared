package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
    name = "system_roles",
    indexes = { @Index(name = "idx_name", columnList = "name") }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_role_id", nullable = false)
    private Integer systemRoleId;

    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "systemRole", fetch = FetchType.LAZY)
    private Set<RolePermission> rolePermissions = new HashSet<>();
}
