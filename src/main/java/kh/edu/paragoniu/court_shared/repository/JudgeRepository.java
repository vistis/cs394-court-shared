package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Judge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, UUID> {
    Optional<Judge> findByLicenseNumber(String licenseNumber);

    List<Judge> findByIsActiveTrue();
}
