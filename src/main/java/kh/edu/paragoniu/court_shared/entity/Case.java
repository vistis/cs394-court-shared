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
    name = "cases",
    indexes = {
        @Index(
            name = "idx_cases_case_number",
            columnList = "case_number",
            unique = true
        ),
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Case implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "case_id", nullable = false)
    private UUID caseId;

    @Column(name = "case_number", unique = true, nullable = false)
    private String caseNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private CaseStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classification_id", nullable = false)
    private CaseClassification classification;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Column(name = "filed_at", nullable = false)
    private Instant filedAt;

    @Column(name = "last_updated_at", nullable = true)
    private Instant lastUpdatedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;
}
