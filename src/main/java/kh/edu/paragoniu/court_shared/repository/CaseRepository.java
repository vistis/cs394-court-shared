package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository
    extends JpaRepository<Case, UUID>, JpaSpecificationExecutor<Case> {
    Optional<Case> findByCaseNumber(String caseNumber);

    @Query(
        "SELECT c FROM Case c WHERE c.isPublic = true AND c.status.name != 'Pending'"
    )
    List<Case> findPubliclyVisibleCases();
}
