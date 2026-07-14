package kh.edu.paragoniu.court_shared.repository;

import java.util.Optional;
import java.util.UUID;
import kh.edu.paragoniu.court_shared.entity.User;
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
}
