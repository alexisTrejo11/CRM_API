package at.backend.MarketingCompany.marketing.activity.api.service;

import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.common.exceptions.InvalidStatusTransitionException;
import at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers.CampaignActivityMappers;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityRepository;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignActivityServiceImpl implements CampaignActivityService {

    private final CampaignActivityRepository campaignActivityRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CampaignActivityMappers campaignActivityMappers;

    @Override
    public Page<CampaignActivityDTO> getAll(Pageable pageable) {
        return campaignActivityRepository.findAll(pageable).map(campaignActivityMappers::entityToDTO);
    }

    @Override
    public CampaignActivityDTO getById(Object id) {
        return campaignActivityRepository.findById(id)
                .map(campaignActivityMappers::entityToDTO)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));
    }

    @Override
    public CampaignActivityDTO create(CampaignActivityInsertDTO input) {
        validate(input);

        CampaignActivityModel activity = campaignActivityMappers.inputToEntity(input);

        MarketingCampaignModel campaign = getCampaign(input.getCampaignId());
        activity.setCampaign(campaign);

        campaignActivityRepository.save(activity);

        return campaignActivityMappers.entityToDTO(activity);
    }

    @Override
    public CampaignActivityDTO update(Long id, CampaignActivityInsertDTO input) {
        CampaignActivityModel existingActivity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityMappers.updateEntity(existingActivity, input);
        campaignActivityRepository.save(existingActivity);

        return campaignActivityMappers.entityToDTO(existingActivity);
    }

    @Override
    public void delete(Object id) {
        CampaignActivityModel activity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityRepository.delete(activity);
    }

    @Override
    public List<CampaignActivityModel> getActivitiesByCampaignId(Long campaignId) {
        return campaignActivityRepository.findByCampaignId(campaignId);
    }

    @Override
    public List<CampaignActivityModel> getActivitiesByStatus(ActivityStatus status) {
        return campaignActivityRepository.findByStatus(status);
    }

    @Override
    public CampaignActivityDTO startActivity(Long id) {
        CampaignActivityModel activity = getActivity(id);
        if (activity.getStatus() != ActivityStatus.PLANNED) {
            throw new InvalidStatusTransitionException(activity.getStatus(), ActivityStatus.PLANNED);
        }
        activity.setStatus(ActivityStatus.IN_PROGRESS);
        activity.setActualStartDate(LocalDateTime.now());
        campaignActivityRepository.save(activity);

        return campaignActivityMappers.entityToDTO(activity);
    }

    @Override
    public CampaignActivityDTO completeActivity(Long id) {
        CampaignActivityModel activity = getActivity(id);

        if (activity.getStatus() != ActivityStatus.IN_PROGRESS) {
            throw new InvalidStatusTransitionException(activity.getStatus(), ActivityStatus.COMPLETED);
        }

        activity.setStatus(ActivityStatus.COMPLETED);
        activity.setActualEndDate(LocalDateTime.now());
        campaignActivityRepository.save(activity);

        return campaignActivityMappers.entityToDTO(activity);
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        CampaignActivityModel activity = getActivity(id);
        return activity.getPlannedCost().subtract(activity.getActualCost() != null ? activity.getActualCost() : BigDecimal.ZERO);
    }

    @Override
    public void validate(CampaignActivityInsertDTO input) {
        if (input.getName() == null || input.getName().isEmpty()) {
            throw new InvalidInputException("Activity name cannot be empty");
        }
        if (input.getPlannedStartDate() == null || input.getPlannedStartDate().isBefore(LocalDateTime.now())) {
            throw new InvalidInputException("Planned start date must be in the future");
        }
        if (input.getPlannedCost() == null || input.getPlannedCost().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException("Planned cost must be greater than zero");
        }
    }

    private CampaignActivityModel getActivity(Long activityID) {
        return campaignActivityRepository.findById(activityID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign Activity not found with ID: " + activityID));
    }

    private MarketingCampaignModel getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }
}