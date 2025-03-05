package at.backend.MarketingCompany.marketing.interaction.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignInteractionRepository extends JpaRepository<CampaignInteractionModel, UUID> {

    List<CampaignInteractionModel> findByCampaignId(UUID campaignId);

    List<CampaignInteractionModel> findByCustomerId(UUID customerId);

    List<CampaignInteractionModel> findByCampaignIdAndInteractionDateBetween(
            UUID campaignId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
