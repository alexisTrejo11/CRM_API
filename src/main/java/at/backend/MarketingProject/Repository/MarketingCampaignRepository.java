package at.backend.MarketingProject.Repository;

import at.backend.MarketingProject.Models.MarketingCampaign;
import at.backend.MarketingProject.Models.Utils.CampaignStatus;
import at.backend.MarketingProject.Models.Utils.CampaignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarketingCampaignRepository extends JpaRepository<MarketingCampaign, Long> {

    List<MarketingCampaign> findByStatus(CampaignStatus status);

    List<MarketingCampaign> findByType(CampaignType type);

    List<MarketingCampaign> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT mc FROM MarketingCampaign mc JOIN mc.relatedDeals d WHERE d.id = :dealId")
    List<MarketingCampaign> findByDealId(@Param("dealId") Long dealId);

    @Query("SELECT mc FROM MarketingCampaign mc WHERE mc.status = 'ACTIVE' " +
            "AND EXISTS (SELECT ci FROM CampaignInteraction ci WHERE ci.campaign = mc AND ci.customer.id = :customerId)")
    List<MarketingCampaign> findActiveByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT DISTINCT mc FROM MarketingCampaign mc " +
            "JOIN mc.targetSegments seg " +
            "JOIN seg.customers cust " +
            "WHERE cust.id = :customerId")
    List<MarketingCampaign> findByCampaignTargetSegmentCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT mc FROM MarketingCampaign mc WHERE mc.endDate < CURRENT_DATE AND mc.status = 'ACTIVE'")
    List<MarketingCampaign> findExpiredActiveCampaigns();

    @Query("SELECT SUM(ca.attributedRevenue) FROM CampaignAttribution ca WHERE ca.campaign.id = :campaignId")
    Optional<Double> findTotalAttributedRevenueByCampaignId(@Param("campaignId") Long campaignId);

    @Query("SELECT mc FROM MarketingCampaign mc WHERE " +
            "EXISTS (SELECT ca FROM CampaignActivity ca WHERE ca.campaign = mc AND ca.status = 'PLANNED' " +
            "AND ca.plannedStartDate BETWEEN :startDate AND :endDate)")
    List<MarketingCampaign> findWithPlannedActivitiesBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT c.* FROM marketing_campaigns c " +
            "JOIN campaign_metrics m ON m.campaign_id = c.id " +
            "WHERE m.name = :metricName AND m.value > m.target_value",
            nativeQuery = true)
    List<MarketingCampaign> findCampaignsExceedingMetricTarget(@Param("metricName") String metricName);
}