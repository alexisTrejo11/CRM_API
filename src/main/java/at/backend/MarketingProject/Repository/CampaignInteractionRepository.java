package at.backend.MarketingProject.Repository;


import at.backend.MarketingProject.Models.CampaignInteraction;
import at.backend.MarketingProject.Models.Utils.InteractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface CampaignInteractionRepository extends JpaRepository<CampaignInteraction, Long> {

    List<CampaignInteraction> findByCampaignId(Long campaignId);

    List<CampaignInteraction> findByCustomerId(Long customerId);

    List<CampaignInteraction> findByCampaignIdAndCustomerId(Long campaignId, Long customerId);

    List<CampaignInteraction> findByInteractionType(InteractionType interactionType);

    List<CampaignInteraction> findByInteractionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT ci FROM CampaignInteraction ci WHERE ci.campaign.id = :campaignId " +
            "AND ci.customer.id IN (SELECT d.customer.id FROM Deal d WHERE d.id = :dealId)")
    List<CampaignInteraction> findByCampaignIdAndDealId(
            @Param("campaignId") Long campaignId,
            @Param("dealId") Long dealId);

    @Query("SELECT ci.interactionType, COUNT(ci) FROM CampaignInteraction ci " +
            "WHERE ci.campaign.id = :campaignId GROUP BY ci.interactionType")
    List<Object[]> countInteractionsByCampaignIdGroupByType(@Param("campaignId") Long campaignId);

    @Query("SELECT COUNT(DISTINCT ci.customer.id) FROM CampaignInteraction ci " +
            "WHERE ci.campaign.id = :campaignId AND ci.interactionType = :interactionType")
    Long countUniqueCustomersByCampaignIdAndInteractionType(
            @Param("campaignId") Long campaignId,
            @Param("interactionType") InteractionType interactionType);


    Long countByCampaignIdAndInteractionType(Long campaignId, InteractionType interactionType);

    @Query("SELECT ci FROM CampaignInteraction ci " +
            "WHERE ci.resultedDeal IS NOT NULL " +
            "AND ci.campaign.id = :campaignId " +
            "ORDER BY ci.interactionDate ASC")
    List<CampaignInteraction> findConversionInteractionsByCampaignId(@Param("campaignId") Long campaignId);

    @Query(value = "SELECT DATE(ci.interaction_date) as date, COUNT(*) as count " +
            "FROM campaign_interactions ci " +
            "WHERE ci.campaign_id = :campaignId " +
            "AND ci.interaction_date BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(ci.interaction_date) " +
            "ORDER BY date ASC",
            nativeQuery = true)
    List<Map<String, Object>> getInteractionCountByDateForCampaign(
            @Param("campaignId") Long campaignId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ci.sourceChannel, COUNT(ci) FROM CampaignInteraction ci " +
            "WHERE ci.campaign.id = :campaignId GROUP BY ci.sourceChannel")
    List<Object[]> countInteractionsByCampaignIdGroupByChannel(@Param("campaignId") Long campaignId);

    @Query("SELECT c.id, c.name, COUNT(ci) as interactionCount " +
            "FROM Customer c JOIN CampaignInteraction ci ON ci.customer.id = c.id " +
            "WHERE ci.campaign.id = :campaignId " +
            "GROUP BY c.id, c.name ORDER BY interactionCount DESC")
    List<Object[]> findMostEngagedCustomersByCampaignId(@Param("campaignId") Long campaignId);
}
