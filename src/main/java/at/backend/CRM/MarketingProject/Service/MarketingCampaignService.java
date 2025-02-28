package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.MarketingProject.AutoMappers.MarketingCampaignMappers;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.MarketingProject.Models.Utils.CampaignStatus;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarketingCampaignService {

    private final MarketingCampaignMappers marketingCampaignMappers;
    private final MarketingCampaignRepository marketingCampaignRepository;

    public MarketingCampaignDTO createCampaign(MarketingCampaignInsertDTO input) {
        MarketingCampaign campaign = marketingCampaignMappers.inputToEntity(input);

        validateCampaign(campaign);

        campaign.setStatus(CampaignStatus.DRAFT);

        marketingCampaignRepository.save(campaign);

        return marketingCampaignMappers.entityToDTO(campaign);
    }

    public MarketingCampaignDTO updateCampaign(Long id, MarketingCampaignInsertDTO insertDTO) {
        MarketingCampaign existingCampaign = getCampaign(id);

        marketingCampaignMappers.updateEntity(existingCampaign, insertDTO);
        marketingCampaignRepository.saveAndFlush(existingCampaign);

        return marketingCampaignMappers.entityToDTO(existingCampaign);
    }

    public void deleteCampaign(Long id) {
        MarketingCampaign campaign = getCampaign(id);

        marketingCampaignRepository.delete(campaign);
    }

    public MarketingCampaignDTO getCampaignById(Long id) {
        MarketingCampaign campaign = getCampaign(id);

        return marketingCampaignMappers.entityToDTO(campaign);
    }

    private MarketingCampaign getCampaign(Long id) {
        return marketingCampaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + id));
    }

    public List<MarketingCampaign> getAllCampaigns() {
        return marketingCampaignRepository.findAll();
    }

    public List<MarketingCampaign> getCampaignsByStatus(CampaignStatus status) {
        return marketingCampaignRepository.findByStatus(status);
    }

    public MarketingCampaign activateCampaign(Long id) {
        MarketingCampaign campaign = getCampaign(id);

        if (campaign.getStatus() == CampaignStatus.DRAFT || campaign.getStatus() == CampaignStatus.PAUSED) {
            campaign.setStatus(CampaignStatus.ACTIVE);
            return marketingCampaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Campaign cannot be activated from current status: " + campaign.getStatus());
        }
    }

    public MarketingCampaign pauseCampaign(Long id) {
        MarketingCampaign campaign = getCampaign(id);
        if (campaign.getStatus() == CampaignStatus.ACTIVE) {
            campaign.setStatus(CampaignStatus.PAUSED);
            return marketingCampaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Campaign cannot be paused from current status: " + campaign.getStatus());
        }
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        MarketingCampaign campaign = getCampaign(id);

        return campaign.getBudget().subtract(campaign.getCostToDate());
    }

    public MarketingCampaign updateCampaignTargets(Long id, Map<String, Double> targets) {
        MarketingCampaign campaign = getCampaign(id);

        campaign.getTargets().putAll(targets);
        return marketingCampaignRepository.save(campaign);
    }

    private void validateCampaign(MarketingCampaign campaign) {
        if (campaign.getName() == null || campaign.getName().isEmpty()) {
            throw new IllegalArgumentException("Campaign name cannot be empty");
        }
        if (campaign.getStartDate() == null || campaign.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date must be in the future");
        }
        if (campaign.getBudget() == null || campaign.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Budget must be greater than zero");
        }
    }
}