package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "legal_representations",
    indexes = { @Index(name = "idx_legal_rep_lawyer_id", columnList = "lawyer_id") }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LegalRepresentationId id = new LegalRepresentationId();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "case_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private Case caseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "participant_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private Participant participantEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "lawyer_id",
        insertable = false,
        updatable = false,
        nullable = false
    )
    private Lawyer lawyerEntity;
}
