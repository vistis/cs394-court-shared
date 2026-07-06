package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.GreffierSupervision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GreffierSupervisionRepository
    extends JpaRepository<GreffierSupervision, UUID>
{
    List<GreffierSupervision> findByChiefGreffierUserId(UUID chiefGreffierId);

    // Verify if a given operator is actually a subordinate of the requesting Chief Greffier
    @Query(
        "SELECT COUNT(gs) > 0 FROM GreffierSupervision gs " +
            "WHERE gs.chiefGreffier.userId = :chiefId AND gs.subordinateGreffier.userId = :subId"
    )
    boolean isSupervisedBy(
        @Param("chiefId") UUID chiefId,
        @Param("subId") UUID subId
    );
}
