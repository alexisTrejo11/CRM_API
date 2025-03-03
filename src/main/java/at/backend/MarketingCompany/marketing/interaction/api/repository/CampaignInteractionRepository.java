package at.backend.MarketingCompany.marketing.interaction.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignInteractionRepository extends JpaRepository<CampaignInteractionModel, Long> {

    List<CampaignInteractionModel> findByCampaignId(Long campaignId);

    List<CampaignInteractionModel> findByCustomerId(Long customerId);

}
