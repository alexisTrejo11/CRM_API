package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CommonClasses.Exceptions.InvalidInputException;
import at.backend.CRM.MarketingProject.AutoMappers.CampaignMetricMappers;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignMetric;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.MarketingProject.Repository.CampaignMetricRepository;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignMetricServiceImpl implements CampaignMetricService {

    private final CampaignMetricRepository campaignMetricRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CampaignMetricMappers campaignMetricMappers;

    @Override
    public CampaignMetricDTO create(CampaignMetricInsertDTO insertDTO) {
        CampaignMetric metric = campaignMetricMappers.inputToEntity(insertDTO);

        validate(insertDTO);

        MarketingCampaign campaign = getCampaign(insertDTO.getCampaignId());
        metric.setCampaign(campaign);

        campaignMetricRepository.save(metric);

        return campaignMetricMappers.entityToDTO(metric);
    }

    @Override
    public CampaignMetricDTO update(Long id, CampaignMetricInsertDTO insertDTO) {
        CampaignMetric existingMetric = getMetric(id);

        campaignMetricMappers.updateEntity(existingMetric, insertDTO);

        campaignMetricRepository.save(existingMetric);

        return campaignMetricMappers.entityToDTO(existingMetric);
    }

    @Override
    public void delete(Long id) {
        CampaignMetric metric = getMetric(id);

        campaignMetricRepository.delete(metric);
    }

    @Override
    public Page<CampaignMetricDTO> getAll(Pageable pageable) {
        return campaignMetricRepository.findAll(pageable).map(campaignMetricMappers::entityToDTO);
    }

    @Override
    public CampaignMetricDTO getById(Long id) {
        CampaignMetric campaignMetric = getMetric(id);

        return campaignMetricMappers.entityToDTO(campaignMetric);
    }

    @Override
    public List<CampaignMetric> getMetricsByCampaignId(Long campaignId) {
        return campaignMetricRepository.findByCampaignId(campaignId);
    }

    public CampaignMetric calculateMetricValue(Long id) {
        CampaignMetric metric = getMetric(id);
        metric.calculateMetricValue();
        return campaignMetricRepository.save(metric);
    }

    public boolean isTargetAchieved(Long id) {
        CampaignMetric metric = getMetric(id);
        return metric.isTargetAchieved();
    }

    @Override
    public void validate(CampaignMetricInsertDTO insertDTO) {
        if (insertDTO.getName() == null || insertDTO.getName().isEmpty()) {
            throw new InvalidInputException("Metric name cannot be empty");
        }
        if (insertDTO.getType() == null) {
            throw new InvalidInputException("Metric type cannot be null");
        }
        if (insertDTO.getTargetValue() == null || insertDTO.getTargetValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException("Target value must be greater than or equal to zero");
        }
    }

    private CampaignMetric getMetric(Long id) {
        return campaignMetricRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Metric not found with ID: " + id));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }
}