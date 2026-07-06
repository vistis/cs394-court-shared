package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.CaseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseAssignmentRepository
    extends JpaRepository<CaseAssignment, UUID>
{
    List<CaseAssignment> findByGreffierEntityUserId(UUID greffierId);

    // Verifies if a regular Greffier has permission to update a specific case record
    @Query(
        "SELECT COUNT(ca) > 0 FROM CaseAssignment ca " +
            "WHERE ca.caseEntity.caseId = :caseId AND ca.greffierEntity.userId = :greffierId"
    )
    boolean isGreffierAssignedToCase(
        @Param("caseId") UUID caseId,
        @Param("greffierId") UUID greffierId
    );
}
