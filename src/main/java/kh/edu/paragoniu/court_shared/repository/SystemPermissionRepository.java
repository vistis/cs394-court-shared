package kh.edu.paragoniu.court_shared.repository;

import java.util.Optional;
import kh.edu.paragoniu.court_shared.entity.SystemPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPermissionRepository
    extends JpaRepository<SystemPermission, Integer>
{
    Optional<SystemPermission> findByCode(String code);
}
