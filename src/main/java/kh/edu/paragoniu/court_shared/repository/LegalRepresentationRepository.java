package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.LegalRepresentation;
import kh.edu.paragoniu.court_shared.entity.LegalRepresentationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalRepresentationRepository
    extends JpaRepository<LegalRepresentation, LegalRepresentationId>
{
    List<LegalRepresentation> findByIdCaseId(UUID caseId);
}
