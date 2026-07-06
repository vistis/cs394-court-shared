package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.UserRole;
import kh.edu.paragoniu.court_shared.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository
    extends JpaRepository<UserRole, UserRoleId>
{
    List<UserRole> findByIdUserId(UUID userId);

    List<UserRole> findByIdSystemRoleId(Integer systemRoleId);

    void deleteByIdUserId(UUID userId);
}
