package at.backend.MarketingProject.Service;

import at.backend.MarketingProject.Models.MarketingCampaign;
import at.backend.MarketingProject.Models.Utils.CampaignStatus;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarketingCampaignService {

    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    public MarketingCampaign createCampaign(MarketingCampaign campaign) {
        validateCampaign(campaign);
        campaign.setStatus(CampaignStatus.DRAFT);
        return marketingCampaignRepository.save(campaign);
    }

    public MarketingCampaign updateCampaign(Long id, MarketingCampaign updatedCampaign) {
        Optional<MarketingCampaign> existingCampaign = marketingCampaignRepository.findById(id);
        if (existingCampaign.isPresent()) {
            MarketingCampaign campaign = existingCampaign.get();
            campaign.setName(updatedCampaign.getName());
            campaign.setDescription(updatedCampaign.getDescription());
            campaign.setStartDate(updatedCampaign.getStartDate());
            campaign.setEndDate(updatedCampaign.getEndDate());
            campaign.setBudget(updatedCampaign.getBudget());
            campaign.setTargetAudience(updatedCampaign.getTargetAudience());
            campaign.setSuccessCriteria(updatedCampaign.getSuccessCriteria());
            campaign.setType(updatedCampaign.getType());
            return marketingCampaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Campaign not found with ID: " + id);
        }
    }

    public void deleteCampaign(Long id) {
        Optional<MarketingCampaign> campaign = marketingCampaignRepository.findById(id);
        if (campaign.isPresent()) {
            marketingCampaignRepository.delete(campaign.get());
        } else {
            throw new RuntimeException("Campaign not found with ID: " + id);
        }
    }

    public MarketingCampaign getCampaignById(Long id) {
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
        MarketingCampaign campaign = getCampaignById(id);
        if (campaign.getStatus() == CampaignStatus.DRAFT || campaign.getStatus() == CampaignStatus.PAUSED) {
            campaign.setStatus(CampaignStatus.ACTIVE);
            return marketingCampaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Campaign cannot be activated from current status: " + campaign.getStatus());
        }
    }

    public MarketingCampaign pauseCampaign(Long id) {
        MarketingCampaign campaign = getCampaignById(id);
        if (campaign.getStatus() == CampaignStatus.ACTIVE) {
            campaign.setStatus(CampaignStatus.PAUSED);
            return marketingCampaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Campaign cannot be paused from current status: " + campaign.getStatus());
        }
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        MarketingCampaign campaign = getCampaignById(id);
        return campaign.getBudget().subtract(campaign.getCostToDate());
    }

    public MarketingCampaign updateCampaignTargets(Long id, Map<String, Double> targets) {
        MarketingCampaign campaign = getCampaignById(id);
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