package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.UUID;

import kh.edu.paragoniu.court_shared.dto.permission.RoleUserSummaryDTO;
import kh.edu.paragoniu.court_shared.entity.UserRole;
import kh.edu.paragoniu.court_shared.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRoleRepository
    extends JpaRepository<UserRole, UserRoleId>
{
    List<UserRole> findByIdUserId(UUID userId);

    List<UserRole> findByIdSystemRoleId(Integer systemRoleId);

    void deleteByIdUserId(UUID userId);

    @Query(
        "SELECT new kh.edu.paragoniu.court_shared.dto.permission.RoleUserSummaryDTO(" +
        "  u.userId, CONCAT(u.firstName, ' ', u.lastName), u.email, u.isActive" +
        ") FROM UserRole ur JOIN ur.user u WHERE ur.systemRole.systemRoleId = :roleId ORDER BY u.lastName"
    )
    List<RoleUserSummaryDTO> findUserSummariesByRoleId(@Param("roleId") Integer roleId);

    long countByIdSystemRoleId(Integer systemRoleId);
}
