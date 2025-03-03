package at.backend.MarketingCompany.marketing.activity.api.repository;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityType;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampaignActivityRepository extends JpaRepository<CampaignActivityModel, Long> {

    List<CampaignActivityModel> findByCampaignId(Long campaignId);

    List<CampaignActivityModel> findByStatus(ActivityStatus status);

    List<CampaignActivityModel> findByActivityType(ActivityType activityType);

    List<CampaignActivityModel> findByAssignedToId(Long userId);

    List<CampaignActivityModel> findByStatusAndPlannedStartDateBefore(ActivityStatus status, LocalDateTime dateTime);

    @Query("SELECT ca FROM CampaignActivityModel ca WHERE ca.campaign.id = :campaignId " +
            "AND ca.status = :status ORDER BY ca.plannedStartDate ASC")
    List<CampaignActivityModel> findByCampaignIdAndStatusOrder(Long id, CampaignType type);

}