package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, UUID> {
    Optional<Lawyer> findByLicenseNumber(String licenseNumber);

    List<Lawyer> findByFirmNameIgnoreCase(String firmName);
}
