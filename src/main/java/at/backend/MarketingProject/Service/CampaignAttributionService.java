package at.backend.MarketingProject.Service;

import at.backend.CRM.Repository.DealRepository;
import at.backend.MarketingProject.AutoMappers.CampaignAttributionMappers;
import at.backend.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingProject.Models.CampaignAttribution;
import at.backend.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.Models.Deal;
import at.backend.MarketingProject.Repository.CampaignAttributionRepository;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignAttributionService {

    private CampaignAttributionRepository campaignAttributionRepository;
    private MarketingCampaignRepository marketingCampaignRepository;
    private DealRepository dealRepository;
    private CampaignAttributionMappers campaignAttributionMappers;

    public CampaignAttributionDTO createAttribution(CampaignAttributionInsertDTO insertDTO) {
        CampaignAttribution attribution = campaignAttributionMappers.inputToEntity(insertDTO);

        validateAttribution(attribution);
        fetchAttributionRelationship(attribution, insertDTO);

        campaignAttributionRepository.save(attribution);

        return campaignAttributionMappers.entityToDTO(attribution);
    }

    public CampaignAttributionDTO updateAttribution(Long id, CampaignAttributionInsertDTO input) {
        CampaignAttribution existingAttribution = campaignAttributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribution not found with ID: " + id));

        campaignAttributionMappers.updateEntity(existingAttribution, input);
        campaignAttributionRepository.save(existingAttribution);

        return campaignAttributionMappers.entityToDTO(existingAttribution);
    }

    public void deleteAttribution(Long id) {
        CampaignAttribution attribution = campaignAttributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribution not found with ID: " + id));

    }

    public CampaignAttributionDTO getAttributionById(Long id) {
        return campaignAttributionRepository.findById(id)
                .map(campaignAttributionMappers::entityToDTO)
                .orElseThrow(() -> new RuntimeException("Attribution not found with ID: " + id));
    }

    public List<CampaignAttributionDTO> getAttributionsByCampaignId(Long campaignId) {
        return campaignAttributionRepository.findByCampaignId(campaignId).stream()
                .map(campaignAttributionMappers::entityToDTO)
                .toList();
    }

    public List<CampaignAttributionDTO> getAttributionsByDealId(Long dealId) {
        return campaignAttributionRepository.findByDealId(dealId).stream()
                .map(campaignAttributionMappers::entityToDTO)
                .toList();
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

    private Deal getDeal(Long dealID){
        return dealRepository.findById(dealID)
                .orElseThrow(() -> new RuntimeException("Deal not found with ID: " + dealID));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignID));
    }


    private void fetchAttributionRelationship(CampaignAttribution attribution, CampaignAttributionInsertDTO insertDTO){
        MarketingCampaign campaign = getCampaign(insertDTO.getCampaignId());
        attribution.setCampaign(campaign);

        Deal deal = getDeal(insertDTO.getDealId());
        attribution.setDeal(deal);
    }
}
