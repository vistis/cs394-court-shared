package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "case_participants",
    indexes = {
        @Index(name = "idx_case_participants_participant_id", columnList = "participant_id")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CaseParticipantId id = new CaseParticipantId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("caseId")
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("participantId")
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participantEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private ParticipantRole participantRole;
}
