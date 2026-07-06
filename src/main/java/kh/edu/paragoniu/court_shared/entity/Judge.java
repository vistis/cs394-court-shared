package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "judges",
    indexes = {
        @Index(
            name = "idx_judges_license",
            columnList = "license_number",
            unique = true
        ),
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "judge_id", nullable = false)
    private UUID judgeId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "license_number", unique = true, nullable = false)
    private String licenseNumber;

    @Column(name = "profile_picture_path", nullable = false)
    private String profilePicturePath;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
