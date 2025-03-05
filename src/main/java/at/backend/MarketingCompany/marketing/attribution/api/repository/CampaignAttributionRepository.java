package at.backend.MarketingCompany.marketing.attribution.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignAttributionRepository extends JpaRepository<CampaignAttributionModel, UUID> {
    List<CampaignAttributionModel> findByCampaignId(UUID campaignId);
    Page<CampaignAttributionModel> findByCampaignId(UUID campaignId, Pageable pageable);
    List<CampaignAttributionModel> findByDealId(UUID dealId);

    boolean existsByDealId(UUID dealId);
    boolean existsByCampaignId(UUID campaignId);

}