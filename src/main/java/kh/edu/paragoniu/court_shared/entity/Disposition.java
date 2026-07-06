package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "dispositions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disposition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "disposition_id", nullable = false)
    private UUID dispositionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "judge_id", nullable = false)
    private Judge judgeEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "outcome_type_id", nullable = false)
    private DispositionOutcome outcomeType;

    @Column(
        name = "ruling_details",
        columnDefinition = "text",
        nullable = false
    )
    private String rulingDetails;

    @Column(name = "effective_at", nullable = false)
    private Instant effectiveAt;
}
