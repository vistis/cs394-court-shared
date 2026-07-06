package kh.edu.paragoniu.court_shared.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Hearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HearingRepository extends JpaRepository<Hearing, UUID> {
    List<Hearing> findByCaseEntityCaseId(UUID caseId);

    List<Hearing> findByCourtroomCourtroomIdAndStatusIgnoreCase(
        Integer courtroomId,
        String status
    );

    @Query(
        "SELECT COUNT(h) > 0 FROM Hearing h " +
            "WHERE h.courtroom.courtroomId = :courtroomId " +
            "AND h.status = 'SCHEDULED' " +
            "AND h.startAt < :endAt AND h.endAt > :startAt " +
            "AND (:excludeHearingId IS NULL OR h.hearingId != :excludeHearingId)"
    )
    boolean hasRoomOverlap(
        @Param("courtroomId") Integer courtroomId,
        @Param("startAt") Instant startAt,
        @Param("endAt") Instant endAt,
        @Param("excludeHearingId") UUID excludeHearingId
    );
}
