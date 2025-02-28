package at.backend.MarketingProject.Repository;

import at.backend.MarketingProject.Models.CampaignActivity;
import at.backend.MarketingProject.Models.Utils.ActivityStatus;
import at.backend.MarketingProject.Models.Utils.ActivityType;
import at.backend.MarketingProject.Models.Utils.CampaignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampaignActivityRepository extends JpaRepository<CampaignActivity, Long> {

    List<CampaignActivity> findByCampaignId(Long campaignId);

    List<CampaignActivity> findByStatus(ActivityStatus status);

    List<CampaignActivity> findByActivityType(ActivityType activityType);

    List<CampaignActivity> findByAssignedToId(Long userId);

    List<CampaignActivity> findByStatusAndPlannedStartDateBefore(ActivityStatus status, LocalDateTime dateTime);

    @Query("SELECT ca FROM CampaignActivity ca WHERE ca.campaign.id = :campaignId " +
            "AND ca.status = :status ORDER BY ca.plannedStartDate ASC")
    List<CampaignActivity> findByCampaignIdAndStatusOrder(Long id, CampaignType type);

}