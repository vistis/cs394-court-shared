package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "case_judges",
    indexes = { @Index(name = "idx_case_judges_judge_id", columnList = "judge_id") }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseJudge implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CaseJudgeId id = new CaseJudgeId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("caseId")
    @JoinColumn(
        name = "case_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private Case caseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("judgeId")
    @JoinColumn(
        name = "judge_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private Judge judgeEntity;

    @Column(name = "is_presiding", nullable = false)
    private boolean isPresiding;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;
}
