package at.backend.CRM.Repository;

import at.backend.CRM.Models.enums.DealStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import at.backend.CRM.Models.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {
    boolean existsByName(String name);


    List<Deal> findByCompanyId(Long companyId);

    List<Deal> findByStage(DealStage stage);

    @Query("SELECT d FROM Deal d WHERE d.expectedCloseDate < :date " +
            "AND d.stage NOT IN ('CLOSED_WON', 'CLOSED_LOST')")
    List<Deal> findOverdueDeals(@Param("date") LocalDateTime date);

    @Query("SELECT d FROM Deal d WHERE d.value >= :minValue " +
            "AND d.stage NOT IN ('CLOSED_LOST')")
    List<Deal> findHighValueDeals(@Param("minValue") BigDecimal minValue);

    @Query("SELECT d.stage, COUNT(d), SUM(d.value) FROM Deal d " +
            "WHERE d.createdAt >= :startDate GROUP BY d.stage")
    List<Object[]> getDealsSummaryByStage(@Param("startDate") LocalDateTime startDate);
}