package at.backend.MarketingProject.Service;


import at.backend.CRM.Repository.DealRepository;
import at.backend.MarketingProject.Models.CampaignAttribution;
import at.backend.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.Models.Deal;
import at.backend.MarketingProject.Repository.CampaignAttributionRepository;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignAttributionService {

    @Autowired
    private CampaignAttributionRepository campaignAttributionRepository;

    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    @Autowired
    private DealRepository dealRepository;

    public CampaignAttribution createAttribution(Long campaignId, Long dealId, CampaignAttribution attribution) {
        validateAttribution(attribution);
        MarketingCampaign campaign = marketingCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignId));
        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new RuntimeException("Deal not found with ID: " + dealId));
        attribution.setCampaign(campaign);
        attribution.setDeal(deal);
        return campaignAttributionRepository.save(attribution);
    }

    public CampaignAttribution updateAttribution(Long id, CampaignAttribution updatedAttribution) {
        Optional<CampaignAttribution> existingAttribution = campaignAttributionRepository.findById(id);
        if (existingAttribution.isPresent()) {
            CampaignAttribution attribution = existingAttribution.get();
            attribution.setAttributionModel(updatedAttribution.getAttributionModel());
            attribution.setAttributionPercentage(updatedAttribution.getAttributionPercentage());
            attribution.setAttributedRevenue(updatedAttribution.getAttributedRevenue());
            attribution.setFirstTouchDate(updatedAttribution.getFirstTouchDate());
            attribution.setLastTouchDate(updatedAttribution.getLastTouchDate());
            attribution.setTouchCount(updatedAttribution.getTouchCount());
            return campaignAttributionRepository.save(attribution);
        } else {
            throw new RuntimeException("Attribution not found with ID: " + id);
        }
    }

    public void deleteAttribution(Long id) {
        Optional<CampaignAttribution> attribution = campaignAttributionRepository.findById(id);
        if (attribution.isPresent()) {
            campaignAttributionRepository.delete(attribution.get());
        } else {
            throw new RuntimeException("Attribution not found with ID: " + id);
        }
    }

    public CampaignAttribution getAttributionById(Long id) {
        return campaignAttributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribution not found with ID: " + id));
    }

    public List<CampaignAttribution> getAttributionsByCampaignId(Long campaignId) {
        return campaignAttributionRepository.findByCampaignId(campaignId);
    }

    public List<CampaignAttribution> getAttributionsByDealId(Long dealId) {
        return campaignAttributionRepository.findByDealId(dealId);
    }

    public BigDecimal calculateTotalAttributedRevenue(Long campaignId) {
        List<CampaignAttribution> attributions = campaignAttributionRepository.findByCampaignId(campaignId);
        return attributions.stream()
                .map(CampaignAttribution::getAttributedRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateAttribution(CampaignAttribution attribution) {
        if (attribution.getAttributionModel() == null) {
            throw new IllegalArgumentException("Attribution model cannot be null");
        }
        if (attribution.getAttributionPercentage() == null || attribution.getAttributionPercentage().compareTo(BigDecimal.ZERO) < 0 ||
                attribution.getAttributionPercentage().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Attribution percentage must be between 0 and 100");
        }
        if (attribution.getAttributedRevenue() == null || attribution.getAttributedRevenue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Attributed revenue must be greater than or equal to zero");
        }
    }
}
