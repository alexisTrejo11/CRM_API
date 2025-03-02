package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Exceptions.BusinessLogicException;
import at.backend.CRM.CommonClasses.Exceptions.InvalidInputException;
import at.backend.CRM.MarketingProject.AutoMappers.MarketingCampaignMappers;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignDTO;
import at.backend.CRM.MarketingProject.DTOs.MarketingCampaignInsertDTO;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.MarketingProject.Utils.Enums.CampaignStatus;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
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
        MarketingCampaign campaign = marketingCampaignMappers.inputToEntity(input);

        validate(input);

        campaign.setStatus(CampaignStatus.DRAFT);

        marketingCampaignRepository.save(campaign);

        return marketingCampaignMappers.entityToDTO(campaign);
    }

    @Override
    public MarketingCampaignDTO update(Long id, MarketingCampaignInsertDTO insertDTO) {
        MarketingCampaign existingCampaign = getCampaign(id);

        marketingCampaignMappers.updateEntity(existingCampaign, insertDTO);
        marketingCampaignRepository.saveAndFlush(existingCampaign);

        return marketingCampaignMappers.entityToDTO(existingCampaign);
    }

    @Override
    public void delete(Long id) {
        MarketingCampaign campaign = getCampaign(id);

        marketingCampaignRepository.delete(campaign);
    }

    @Override
    public MarketingCampaignDTO getById(Long id) {
        MarketingCampaign campaign = getCampaign(id);

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
        MarketingCampaign campaign = getCampaign(id);

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
        MarketingCampaign campaign = getCampaign(id);
        if (campaign.getStatus() == CampaignStatus.ACTIVE) {
            campaign.setStatus(CampaignStatus.PAUSED);
            marketingCampaignRepository.save(campaign);
            return marketingCampaignMappers.entityToDTO(campaign);
        } else {
            throw new BusinessLogicException("Campaign cannot be paused from current status: " + campaign.getStatus());
        }
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        MarketingCampaign campaign = getCampaign(id);

        return campaign.getBudget().subtract(campaign.getCostToDate());
    }

    @Override
    public MarketingCampaignDTO updateCampaignTargets(Long id, Map<String, Double> targets) {
        MarketingCampaign campaign = getCampaign(id);

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

    private MarketingCampaign getCampaign(Long id) {
        return marketingCampaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + id));
    }
}