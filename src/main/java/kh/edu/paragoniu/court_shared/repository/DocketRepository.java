package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Docket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocketRepository extends MongoRepository<Docket, String> {
    List<Docket> findByCaseId(UUID caseId);
}
