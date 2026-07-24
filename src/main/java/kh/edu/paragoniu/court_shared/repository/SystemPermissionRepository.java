package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;

import kh.edu.paragoniu.court_shared.dto.permission.PermissionListItemDTO;
import kh.edu.paragoniu.court_shared.entity.SystemPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPermissionRepository
    extends JpaRepository<SystemPermission, Integer>
{
    Optional<SystemPermission> findByCode(String code);

    List<SystemPermission> findAllByOrderByCode();

    @Query(
        "SELECT new kh.edu.paragoniu.court_shared.dto.permission.PermissionListItemDTO(" + 
        " sp.systemPermissionId, sp.code, " +
        " (SELECT COUNT(rp) FROM RolePermission rp WHERE rp.systemPermission = sp)" +
        " ) FROM SystemPermission sp ORDER BY sp.code"
    )
    List<PermissionListItemDTO> findPermissionListItems();

    boolean existsByCodeIgnoreCase(String code);
    Optional<SystemPermission> findByCodeIgnoreCase(String code);

}
