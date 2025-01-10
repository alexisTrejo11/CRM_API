package at.backend.CRM.Repository;

import at.backend.CRM.Models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByAssignedUserId(Long userId);

    List<Activity> findByDealId(Long dealId);

    List<Activity> findByContactId(Long contactId);

    @Query("SELECT a FROM Activity a WHERE a.dueDate <= :date " +
            "AND a.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Activity> findPendingActivities(@Param("date") LocalDateTime date);

    @Query("SELECT a FROM Activity a WHERE a.assignedUser.id = :userId " +
            "AND a.status = 'PENDING' AND a.dueDate <= :date")
    List<Activity> findOverdueActivitiesForUser(
            @Param("userId") Long userId,
            @Param("date") LocalDateTime date
    );
}
