package at.backend.CRM.MarketingProject.Repository;


import at.backend.CRM.MarketingProject.Models.CampaignInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignInteractionRepository extends JpaRepository<CampaignInteraction, Long> {

    List<CampaignInteraction> findByCampaignId(Long campaignId);

    List<CampaignInteraction> findByCustomerId(Long customerId);

}
