package at.backend.MarketingCompany.marketing.attribution.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignAttributionRepository extends JpaRepository<CampaignAttributionModel, Long> {
    List<CampaignAttributionModel> findByCampaignId(Long campaignId);
    List<CampaignAttributionModel> findByDealId(Long dealId);
}