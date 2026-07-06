package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Documents;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository
    extends MongoRepository<Documents, String>
{
    List<Documents> findByCaseId(UUID caseId);
}
