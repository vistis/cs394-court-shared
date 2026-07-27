package kh.edu.paragoniu.court_shared.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    // Grabs the active user by username. Roles and permissions are loaded
    // separately via UserRoleRepository / RolePermissionRepository, since the
    // User entity has no direct association to them.
    @Query(
        "SELECT u FROM User u WHERE u.username = :username AND u.isActive = true"
    )
    Optional<User> findActiveByUsername(@Param("username") String username);

    // Same as above but keyed on the (unique) email address, used by panels
    // that authenticate users by email rather than username.
    @Query(
        "SELECT u FROM User u WHERE u.email = :email AND u.isActive = true"
    )
    Optional<User> findActiveByEmail(@Param("email") String email);
    // Grabs user, roles, and permissions
    // @Query(
    //     "SELECT u FROM User u " +
    //         "LEFT JOIN FETCH u.username WHERE u.username = :username AND u.isActive = true"
    // )

    @Query(
        "SELECT DISTINCT u FROM User u " +
        "LEFT JOIN FETCH u.userRoles ur " +
        "LEFT JOIN FETCH ur.systemRole sr " +
        "LEFT JOIN FETCH sr.rolePermissions rp " +
        "LEFT JOIN FETCH rp.systemPermission sp " +
        "WHERE (u.username = :usernameOrEmail OR u.email = :usernameOrEmail) AND u.isActive = true"
    )
    Optional<User> findAuthenticatedUserByUsernameOrEmail(
        @Param("usernameOrEmail") String usernameOrEmail
    );

    @Query(
        "SELECT u.userId FROM User u WHERE " +
        "(:q IS NULL OR :q = '' " +
        " OR u.username LIKE LOWER(CONCAT('%', :q, '%')) " +
        " OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :q, '%')) " +
        " OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :q, '%')) " +
        " OR LOWER(u.email) LIKE LOWER(CONCAT('%', :q, '%')))" +
        "AND (:status IS NULL OR u.isActive = :status)"
    )
    Page<UUID> searchUserIds(@Param("q") String q, @Param("status") Boolean status, Pageable pageable);

    @Query(
        "SELECT DISTINCT u FROM User u " +
        "LEFT JOIN FETCH u.userRoles ur " +
        "LEFT JOIN FETCH ur.systemRole sr " +
        "WHERE u.userId IN :ids"
    )
    List<User> findByIdsWithRoles(@Param("ids") List<UUID> ids);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    long countByIsActive(boolean isactive);

    @Query(
        "SELECT DISTINCT u FROM User u " +
        "LEFT JOIN FETCH u.userRoles ur " +
        "LEFT JOIN FETCH ur.systemRole sr " +
        "WHERE u.userId = :userId"
    )
    Optional<User> findByIdWithRoles(@Param("userId") UUID userId);

    Optional<User> findByEmail(String email);

    @Query(
        "SELECT u FROM User u WHERE u.username = :username AND u.email = :email"
    )
    Optional<User> findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    boolean existsByUserIdAndUserRolesSystemRoleIsDefaultTrue(UUID userId);
}
