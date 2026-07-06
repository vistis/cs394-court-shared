package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, UUID> {
    List<Appeal> findByOriginalCaseCaseId(UUID originalCaseId);

    boolean existsByOriginalCaseCaseIdAndStatusIgnoreCase(
        UUID originalCaseId,
        String status
    );
}
