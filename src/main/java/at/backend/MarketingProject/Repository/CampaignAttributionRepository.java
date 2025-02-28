package at.backend.MarketingProject.Repository;


import at.backend.MarketingProject.Models.CampaignAttribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignAttributionRepository extends JpaRepository<CampaignAttribution, Long> {
    List<CampaignAttribution> findByCampaignId(Long campaignId);
    List<CampaignAttribution> findByDealId(Long dealId);
}