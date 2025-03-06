package at.backend.MarketingCompany.marketing.activity.api.service;

import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.common.exceptions.InvalidStatusTransitionException;
import at.backend.MarketingCompany.marketing.activity.domain.CampaignActivity;
import at.backend.MarketingCompany.marketing.activity.infrastructure.AutoMappers.ActivityMappers;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityDTO;
import at.backend.MarketingCompany.marketing.activity.infrastructure.DTOs.CampaignActivityInsertDTO;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityRepository;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers.CampaignMappers;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignActivityServiceImpl implements CampaignActivityService {

    private final CampaignActivityRepository campaignActivityRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CampaignMappers campaignMappers;
    private final ActivityMappers activityMappers;

    @Override
    public Page<CampaignActivityDTO> getAll(Pageable pageable) {
        return campaignActivityRepository.findAll(pageable)
                .map(activityMappers::modelToDTO);
    }

    @Override
    public CampaignActivityDTO getById(UUID id) {
        return campaignActivityRepository.findById(id)
                .map(activityMappers::modelToDTO)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));
    }

    @Override
    public CampaignActivityDTO create(CampaignActivityInsertDTO input) {
        validate(input);

        CampaignActivity activity = activityMappers.inputToEntity(input);

        getCampaign(input.getCampaignId());
        activity.setCampaignId(CampaignId.of(input.getCampaignId()));

        save(activity);

        return activityMappers.domainToDTO(activity);
    }

    @Override
    public CampaignActivityDTO update(UUID id, CampaignActivityInsertDTO input) {
        CampaignActivity existingActivity = getActivity(id);

        activityMappers.updateEntity(existingActivity, input);
        save(existingActivity);

        return activityMappers.domainToDTO(existingActivity);
    }

    @Override
    public void delete(UUID id) {
        CampaignActivityModel activity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityRepository.delete(activity);
    }

    @Override
    public List<CampaignActivityDTO> getActivitiesByCampaignId(UUID campaignId) {
        return campaignActivityRepository.findByCampaignId(campaignId)
                .stream()
                .map(activityMappers::modelToDTO)
                .toList();
    }

    @Override
    public List<CampaignActivityDTO> getActivitiesByStatus(ActivityStatus status) {
        return campaignActivityRepository.findByStatus(status)
                .stream()
                .map(activityMappers::modelToDTO)
                .toList();
    }

    @Override
    public CampaignActivityDTO startActivity(UUID id) {
        CampaignActivity activity = getActivity(id);
        if (activity.getStatus() != ActivityStatus.PLANNED) {
            throw new InvalidStatusTransitionException(activity.getStatus(), ActivityStatus.PLANNED);
        }
        activity.startActivity();

        save(activity);

        return activityMappers.domainToDTO(activity);
    }

    @Override
    public CampaignActivityDTO completeActivity(UUID id) {
        CampaignActivity activity = getActivity(id);

        if (activity.getStatus() != ActivityStatus.IN_PROGRESS) {
            throw new InvalidStatusTransitionException(activity.getStatus(), ActivityStatus.COMPLETED);
        }

        activity.completeActivity();
        save(activity);

        return activityMappers.domainToDTO(activity);
    }

    public BigDecimal calculateRemainingBudget(UUID id) {
        var activity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + id));

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

    private CampaignActivity getActivity(UUID activityID) {
        return campaignActivityRepository.findById(activityID)
                .map(activityMappers::modelToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Campaign Activity not found with ID: " + activityID));
    }

    private MarketingCampaign getCampaign(UUID campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .map(campaignMappers::modelToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }

    private void save(CampaignActivity activity) {
        CampaignActivityModel model = activityMappers.domainToModel(activity);
        campaignActivityRepository.save(model);
    }
}