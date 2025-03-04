package at.backend.MarketingCompany.marketing.campaign.api.service;

import at.backend.MarketingCompany.common.exceptions.CampaignServiceException;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignStatus;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.*;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers.MarketingCampaignMappers;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketingCampaignServiceImpl implements MarketingCampaignService {

    private final MarketingCampaignMappers campaignMappers;
    private final MarketingCampaignRepository campaignRepository;

    @Override
    public List<MarketingCampaignDTO> getActiveCampaigns(LocalDate date) {
        return campaignRepository.findByStatus(CampaignStatus.ACTIVE)
                .stream()
                .map(campaignMappers::modelToDTO)
                .toList();
    }

    @Override
    public MarketingCampaignDTO startCampaign(UUID campaignId) {
        MarketingCampaign campaign = getCampaign(campaignId);

        campaign.startCampaign();

        saveCampaign(campaign);

        return campaignMappers.domainToDTO(campaign);
    }

    @Override
    public MarketingCampaignDTO completeCampaign(UUID campaignId) {
        MarketingCampaign campaign = getCampaign(campaignId);

        campaign.completeCampaign();

        saveCampaign(campaign);

        return campaignMappers.domainToDTO(campaign);
    }

    @Override
    public MarketingCampaignDTO updateBudget(UUID campaignId, BigDecimal newBudget) {
        MarketingCampaign campaign = getCampaign(campaignId);
        campaign.addCost(newBudget);

        saveCampaign(campaign);

        return campaignMappers.domainToDTO(campaign);
    }

    @Override
    public double calculateCampaignROI(UUID campaignId) {
       MarketingCampaign campaign = getCampaign(campaignId);

       return campaign.calculateROI();
    }

    @Override
    public void archiveCampaign(UUID campaignId) {
        MarketingCampaign campaign = getCampaign(campaignId);

        campaign.archive();

        saveCampaign(campaign);
    }

    @Override
    public Page<MarketingCampaignDTO> getAll(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(campaignMappers::modelToDTO);
    }

    @Override
    public MarketingCampaignDTO getById(UUID id) {
        MarketingCampaign campaign = getCampaign(id);

        return campaignMappers.domainToDTO(campaign);
    }

    @Override
    public MarketingCampaignDTO create(MarketingCampaignInsertDTO insertDTO) {
        validate(insertDTO);

        MarketingCampaign campaign = generateCampaign(insertDTO);

        saveCampaign(campaign);

        return campaignMappers.domainToDTO(campaign);
    }

    @Override
    public MarketingCampaignDTO update(UUID id, MarketingCampaignInsertDTO insertDTO) {
        validate(insertDTO);

        MarketingCampaign campaign = getCampaign(id);
        campaignMappers.updateEntity(campaign, insertDTO);

        saveCampaign(campaign);

        return campaignMappers.domainToDTO(campaign);

    }

    @Override
    public void delete(UUID id) {
        getCampaign(id);

        campaignRepository.deleteById(id);
    }

    @Override
    public void validate(MarketingCampaignInsertDTO input) {
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            throw new CampaignServiceException("Campaign name is required");
        }
        if (input.getStartDate().isBefore(LocalDate.now())) {
            throw new CampaignServiceException("Start date cannot be in the past");
        }
        if (input.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CampaignServiceException("Budget must be positive");
        }
    }

    /*
     private double calculateProgress(double current, double target) {
        return target != 0 ? (current / target) * 100 : 0;
    }
     */

    private void saveCampaign(MarketingCampaign campaign) {
        MarketingCampaignModel campaignModel = campaignMappers.domainToModel(campaign);
        campaignRepository.save(campaignModel);
    }

    private MarketingCampaign getCampaign(UUID id) {
        return campaignRepository.findById(id)
                .map(campaignMappers::modelToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
    }

    private MarketingCampaign generateCampaign(MarketingCampaignInsertDTO insertDTO) {
        CampaignPeriod period = new CampaignPeriod(insertDTO.getStartDate(), insertDTO.getEndDate());
        Budget campaignBudget = new Budget(insertDTO.getBudget(), BigDecimal.ZERO);
        TargetAudience audience = campaignMappers.toTargetAudience(insertDTO.getTargetAudience());
        SuccessCriteria criteria = campaignMappers.toSuccessCriteria(insertDTO.getSuccessCriteria());

        // TODO: Validate Fields
        return MarketingCampaign.builder()
                .name(insertDTO.getName())
                .description(insertDTO.getDescription())
                .period(period)
                .budget(campaignBudget)
                .targetAudience(audience)
                .type(insertDTO.getType())
                .successCriteria(criteria)
                .build();
    }
}