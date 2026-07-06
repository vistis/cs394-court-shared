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

    // Grabs user, roles, and permissions
    @Query(
        "SELECT u FROM User u " +
            "LEFT JOIN FETCH u.username WHERE u.username = :username AND u.isActive = true"
    )
    Optional<User> findAuthenticatedUserWithAuthorities(
        @Param("username") String username
    );
}
