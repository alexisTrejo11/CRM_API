package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Exceptions.InvalidInputException;
import at.backend.CRM.CommonClasses.Exceptions.InvalidStatusTransitionException;
import at.backend.CRM.MarketingProject.AutoMappers.CampaignActivityMappers;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignActivityInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignActivity;
import at.backend.CRM.MarketingProject.Utils.Enums.ActivityStatus;
import at.backend.CRM.MarketingProject.Repository.CampaignActivityRepository;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import at.backend.CRM.MarketingProject.Models.MarketingCampaign;

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
    public CampaignActivityDTO getById(Long id) {
        return campaignActivityRepository.findById(id)
                .map(campaignActivityMappers::entityToDTO)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));
    }

    @Override
    public CampaignActivityDTO create(CampaignActivityInsertDTO input) {
        validate(input);

        CampaignActivity activity = campaignActivityMappers.inputToEntity(input);

        MarketingCampaign campaign = getCampaign(input.getCampaignId());
        activity.setCampaign(campaign);

        campaignActivityRepository.save(activity);

        return campaignActivityMappers.entityToDTO(activity);
    }

    @Override
    public CampaignActivityDTO update(Long id, CampaignActivityInsertDTO input) {
        CampaignActivity existingActivity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityMappers.updateEntity(existingActivity, input);
        campaignActivityRepository.save(existingActivity);

        return campaignActivityMappers.entityToDTO(existingActivity);
    }

    @Override
    public void delete(Long id) {
        CampaignActivity activity = campaignActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with ID: " + id));

        campaignActivityRepository.delete(activity);
    }

    @Override
    public List<CampaignActivity> getActivitiesByCampaignId(Long campaignId) {
        return campaignActivityRepository.findByCampaignId(campaignId);
    }

    @Override
    public List<CampaignActivity> getActivitiesByStatus(ActivityStatus status) {
        return campaignActivityRepository.findByStatus(status);
    }

    @Override
    public CampaignActivityDTO startActivity(Long id) {
        CampaignActivity activity = getActivity(id);
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
        CampaignActivity activity = getActivity(id);

        if (activity.getStatus() != ActivityStatus.IN_PROGRESS) {
            throw new InvalidStatusTransitionException(activity.getStatus(), ActivityStatus.COMPLETED);
        }

        activity.setStatus(ActivityStatus.COMPLETED);
        activity.setActualEndDate(LocalDateTime.now());
        campaignActivityRepository.save(activity);

        return campaignActivityMappers.entityToDTO(activity);
    }

    public BigDecimal calculateRemainingBudget(Long id) {
        CampaignActivity activity = getActivity(id);
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

    private CampaignActivity getActivity(Long activityID) {
        return campaignActivityRepository.findById(activityID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign Activity not found with ID: " + activityID));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }
}