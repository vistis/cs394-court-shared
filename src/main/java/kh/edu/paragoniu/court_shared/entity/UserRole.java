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
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRoleId id = new UserRoleId();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(
        name = "user_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("systemRoleId")
    @JoinColumn(
        name = "system_role_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private SystemRole systemRole;
}
