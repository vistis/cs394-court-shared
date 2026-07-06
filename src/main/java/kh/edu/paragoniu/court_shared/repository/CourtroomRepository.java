package kh.edu.paragoniu.court_shared.repository;

import java.util.Optional;
import kh.edu.paragoniu.court_shared.entity.Courtroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtroomRepository extends JpaRepository<Courtroom, Integer> {
    Optional<Courtroom> findByRoomNumber(String roomNumber);
}
