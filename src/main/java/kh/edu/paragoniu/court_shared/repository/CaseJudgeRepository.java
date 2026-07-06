package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.CaseJudge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseJudgeRepository extends JpaRepository<CaseJudge, UUID> {
    List<CaseJudge> findByIdCaseId(UUID caseId);
}
