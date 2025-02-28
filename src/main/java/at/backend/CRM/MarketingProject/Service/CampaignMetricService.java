package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.MarketingProject.AutoMappers.CampaignMetricMappers;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignMetric;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.MarketingProject.Repository.CampaignMetricRepository;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignMetricService {

    private final CampaignMetricRepository campaignMetricRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CampaignMetricMappers campaignMetricMappers;

    public CampaignMetricDTO createMetric(CampaignMetricInsertDTO insertDTO) {
        CampaignMetric metric = campaignMetricMappers.inputToEntity(insertDTO);

        validateMetric(metric);

        MarketingCampaign campaign = getCampaign(insertDTO.getCampaignId());
        metric.setCampaign(campaign);

        campaignMetricRepository.save(metric);

        return campaignMetricMappers.entityToDTO(metric);
    }

    public CampaignMetricDTO updateMetric(Long id, CampaignMetricInsertDTO insertDTO) {
        CampaignMetric existingMetric = getMetric(id);

        campaignMetricMappers.updateEntity(existingMetric, insertDTO);

        campaignMetricRepository.save(existingMetric);

        return campaignMetricMappers.entityToDTO(existingMetric);
    }

    public void deleteMetric(Long id) {
        CampaignMetric metric = getMetric(id);

        campaignMetricRepository.delete(metric);
    }

    public CampaignMetricDTO getMetricById(Long id) {
        CampaignMetric campaignMetric = getMetric(id);

        return campaignMetricMappers.entityToDTO(campaignMetric);
    }

    private CampaignMetric getMetric(Long id) {
        return campaignMetricRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metric not found with ID: " + id));
    }

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

    private void validateMetric(CampaignMetric metric) {
        if (metric.getName() == null || metric.getName().isEmpty()) {
            throw new IllegalArgumentException("Metric name cannot be empty");
        }
        if (metric.getType() == null) {
            throw new IllegalArgumentException("Metric type cannot be null");
        }
        if (metric.getTargetValue() == null || metric.getTargetValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Target value must be greater than or equal to zero");
        }
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignID));
    }
}