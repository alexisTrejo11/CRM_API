package at.backend.MarketingCompany.marketing.campaign.api.service;

import at.backend.MarketingCompany.common.exceptions.BusinessLogicException;
import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers.MarketingCampaignMappers;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignStatus;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarketingCampaignServiceImpl implements MarketingCampaignService {

    private final MarketingCampaignMappers marketingCampaignMappers;
    private final MarketingCampaignRepository marketingCampaignRepository;

    @Override
    public MarketingCampaignDTO create(MarketingCampaignInsertDTO input) {
        MarketingCampaignModel campaign = marketingCampaignMappers.inputToEntity(input);

        validate(input);

        campaign.setStatus(CampaignStatus.DRAFT);

        marketingCampaignRepository.save(campaign);

        return marketingCampaignMappers.entityToDTO(campaign);
    }

    @Override
    public MarketingCampaignDTO update(Long id, MarketingCampaignInsertDTO insertDTO) {
        MarketingCampaignModel existingCampaign = getCampaign(id);

        marketingCampaignMappers.updateEntity(existingCampaign, insertDTO);
        marketingCampaignRepository.saveAndFlush(existingCampaign);

        return marketingCampaignMappers.entityToDTO(existingCampaign);
    }

    @Override
    public void delete(Long id) {
        MarketingCampaignModel campaign = getCampaign(id);

        marketingCampaignRepository.delete(campaign);
    }

    @Override
    public MarketingCampaignDTO getById(Long id) {
        MarketingCampaignModel campaign = getCampaign(id);

        return marketingCampaignMappers.entityToDTO(campaign);
    }

    @Override
    public Page<MarketingCampaignDTO> getAll(Pageable pageable) {
        return marketingCampaignRepository.findAll(pageable).map(marketingCampaignMappers::entityToDTO);
    }

    @Override
    public List<MarketingCampaignDTO> getCampaignsByStatus(CampaignStatus status) {
        return marketingCampaignRepository.findByStatus(status)
                .stream()
                .map(marketingCampaignMappers::entityToDTO)
                .toList();
    }

    @Override
    public MarketingCampaignDTO activateCampaign(Long id) {
        MarketingCampaignModel campaign = getCampaign(id);

        if (campaign.getStatus() == CampaignStatus.DRAFT || campaign.getStatus() == CampaignStatus.PAUSED) {
            campaign.setStatus(CampaignStatus.ACTIVE);
            marketingCampaignRepository.save(campaign);
            return marketingCampaignMappers.entityToDTO(campaign);
        } else {
            throw new RuntimeException("Campaign cannot be activated from current status: " + campaign.getStatus());
        }
    }

    @Override
    public MarketingCampaignDTO pauseCampaign(Long id) {
        MarketingCampaignModel campaign = getCampaign(id);
        if (campaign.getStatus() == CampaignStatus.ACTIVE) {
            campaign.setStatus(CampaignStatus.PAUSED);
            marketingCampaignRepository.save(campaign);
            return marketingCampaignMappers.entityToDTO(campaign);
        } else {
            throw new BusinessLogicException("Campaign cannot be paused from current status: " + campaign.getStatus());
        }
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        MarketingCampaignModel campaign = getCampaign(id);

        return campaign.getBudget().subtract(campaign.getCostToDate());
    }

    @Override
    public MarketingCampaignDTO updateCampaignTargets(Long id, Map<String, Double> targets) {
        MarketingCampaignModel campaign = getCampaign(id);

        campaign.getTargets().putAll(targets);
        marketingCampaignRepository.save(campaign);
        return marketingCampaignMappers.entityToDTO(campaign);
    }

    @Override
    public void validate(MarketingCampaignInsertDTO insertDTO) {
        if (insertDTO.getName() == null || insertDTO.getName().isEmpty()) {
            throw new InvalidInputException("Campaign name cannot be empty");
        }
        if (insertDTO.getStartDate() == null || insertDTO.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidInputException("Start date must be in the future");
        }
        if (insertDTO.getBudget() == null || insertDTO.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("Budget must be greater than zero");
        }
    }

    private MarketingCampaignModel getCampaign(Long id) {
        return marketingCampaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + id));
    }
}