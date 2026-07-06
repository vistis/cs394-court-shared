package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import kh.edu.paragoniu.court_shared.entity.RolePermission;
import kh.edu.paragoniu.court_shared.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository
    extends JpaRepository<RolePermission, RolePermissionId>
{
    List<RolePermission> findByIdSystemRoleId(Integer systemRoleId);
}
