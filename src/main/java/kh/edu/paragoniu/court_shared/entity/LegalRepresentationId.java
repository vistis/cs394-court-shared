package kh.edu.paragoniu.court_shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalRepresentationId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "case_id", nullable = false)
    private UUID caseId;

    @Column(name = "participant_id", nullable = false)
    private UUID participantId;

    @Column(name = "lawyer_id", nullable = false)
    private UUID lawyerId;
}
