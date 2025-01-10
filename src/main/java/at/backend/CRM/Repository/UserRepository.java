package at.backend.CRM.Repository;

import at.backend.CRM.Models.User;
import at.backend.CRM.Models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);

    @Query("SELECT u FROM User u WHERE SIZE(u.activities) >= :minActivities " +
            "AND u.role = 'SALES_REP'")
    List<User> findActiveSalesReps(@Param("minActivities") int minActivities);

    @Query("SELECT u.id, u.firstName, u.lastName, COUNT(a) " +
            "FROM User u LEFT JOIN u.activities a " +
            "WHERE a.createdAt >= :startDate " +
            "GROUP BY u.id, u.firstName, u.lastName")
    List<Object[]> getUserActivityStats(@Param("startDate") LocalDateTime startDate);
}
