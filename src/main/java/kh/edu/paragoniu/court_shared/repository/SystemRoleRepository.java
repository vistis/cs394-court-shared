package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;
import kh.edu.paragoniu.court_shared.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRoleRepository
    extends JpaRepository<SystemRole, Integer>
{
    Optional<SystemRole> findByNameIgnoreCase(String name);
    List<SystemRole> findAll();
}
