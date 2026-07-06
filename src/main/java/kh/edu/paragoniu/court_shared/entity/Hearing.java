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
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "hearings",
    indexes = {
        @Index(name = "idx_case_id", columnList = "case_id"),
        @Index(
            name = "idx_courtroom_collision_check",
            columnList = "courtroom_id, status, start_at, end_at"
        ),
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hearing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hearing_id")
    private UUID hearingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courtroom_id", nullable = false)
    private Courtroom courtroom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hearing_type_id", nullable = false)
    private HearingType hearingType;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "end_at", nullable = false)
    private Instant endAt;

    @Column(nullable = false)
    private String status;
}
