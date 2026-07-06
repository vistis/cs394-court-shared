package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.CaseParticipant;
import kh.edu.paragoniu.court_shared.entity.CaseParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseParticipantRepository
    extends JpaRepository<CaseParticipant, CaseParticipantId>
{
    List<CaseParticipant> findByIdCaseId(UUID caseId);
}
