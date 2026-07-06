package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "greffier_supervisions",
    indexes = {
        @Index(
            name = "idx_chief_greffier_user_id",
            columnList = "chief_greffier_user_id"
        ),
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreffierSupervision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "supervision_id", nullable = false)
    private UUID supervisionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chief_greffier_user_id", nullable = false)
    private User chiefGreffier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "subordinate_greffier_user_id",
        unique = true,
        nullable = false
    )
    private User subordinateGreffier;
}
