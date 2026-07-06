package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository
    extends JpaRepository<Participant, UUID>
{
    List<Participant> findByNameContainingIgnoreCase(String name);
}
