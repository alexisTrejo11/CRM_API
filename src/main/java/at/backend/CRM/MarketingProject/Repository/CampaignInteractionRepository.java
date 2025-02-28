package at.backend.CRM.MarketingProject.Repository;


import at.backend.CRM.MarketingProject.Models.CampaignInteraction;
import at.backend.CRM.MarketingProject.Models.Utils.InteractionType;
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

}
