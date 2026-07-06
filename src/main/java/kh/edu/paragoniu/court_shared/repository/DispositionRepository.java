package kh.edu.paragoniu.court_shared.repository;

import java.util.Optional;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Disposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositionRepository
    extends JpaRepository<Disposition, UUID>
{
    Optional<Disposition> findByCaseEntityCaseId(UUID caseId);

    @Query(
        "SELECT d FROM Disposition d WHERE d.caseEntity.caseId = :caseId AND d.effectiveAt <= CURRENT_TIMESTAMP"
    )
    Optional<Disposition> findActiveFinalRuling(@Param("caseId") UUID caseId);
}
