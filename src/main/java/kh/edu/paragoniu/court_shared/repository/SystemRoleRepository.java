package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;

import kh.edu.paragoniu.court_shared.dto.permission.RoleListItemDTO;
import kh.edu.paragoniu.court_shared.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

@Repository
public interface SystemRoleRepository
    extends JpaRepository<SystemRole, Integer>
{
    Optional<SystemRole> findByNameIgnoreCase(String name);
    List<SystemRole> findAll();

    boolean existsByNameIgnoreCase(String name);

    @Query(
        "SELECT new kh.edu.paragoniu.court_shared.dto.permission.RoleListItemDTO(" +
        "  sr.systemRoleId, sr.name, " +
        "  (SELECT COUNT(ur) FROM UserRole ur WHERE ur.systemRole = sr), " +
        "  (SELECT COUNT(rp) FROM RolePermission rp WHERE rp.systemRole = sr)" +
        ") FROM SystemRole sr ORDER BY sr.name"
    )
    List<RoleListItemDTO> findRoleListItems();

    @Query(
        "SELECT DISTINCT sr FROM SystemRole sr " +
        "LEFT JOIN FETCH sr.rolePermissions rp " +
        "LEFT JOIN FETCH rp.systemPermission sp " +
        "WHERE sr.systemRoleId = :roleId"
    )
    Optional<SystemRole> findByIdWithPermissions(@Param("roleId") Integer roleId);

    boolean existsBySystemRoleIdAndIsDefaultTrue(Integer systemRoleId);

    
}
