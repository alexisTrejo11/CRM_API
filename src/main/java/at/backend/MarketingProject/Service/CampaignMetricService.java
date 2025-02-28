package at.backend.MarketingProject.Service;

import at.backend.MarketingProject.Models.CampaignMetric;
import at.backend.MarketingProject.Models.MarketingCampaign;
import at.backend.MarketingProject.Repository.CampaignMetricRepository;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignMetricService {

    private final CampaignMetricRepository campaignMetricRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;

    public CampaignMetric createMetric(Long campaignId, CampaignMetric metric) {
        validateMetric(metric);
        MarketingCampaign campaign = marketingCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignId));
        metric.setCampaign(campaign);
        return campaignMetricRepository.save(metric);
    }

    public CampaignMetric updateMetric(Long id, CampaignMetric updatedMetric) {
        Optional<CampaignMetric> existingMetric = campaignMetricRepository.findById(id);
        if (existingMetric.isPresent()) {
            CampaignMetric metric = existingMetric.get();
            metric.setName(updatedMetric.getName());
            metric.setDescription(updatedMetric.getDescription());
            metric.setType(updatedMetric.getType());
            metric.setValue(updatedMetric.getValue());
            metric.setTargetValue(updatedMetric.getTargetValue());
            metric.setMeasurementUnit(updatedMetric.getMeasurementUnit());
            metric.setCalculationFormula(updatedMetric.getCalculationFormula());
            metric.setDataSource(updatedMetric.getDataSource());
            metric.setAutomated(updatedMetric.isAutomated());
            return campaignMetricRepository.save(metric);
        } else {
            throw new RuntimeException("Metric not found with ID: " + id);
        }
    }

    public void deleteMetric(Long id) {
        Optional<CampaignMetric> metric = campaignMetricRepository.findById(id);
        if (metric.isPresent()) {
            campaignMetricRepository.delete(metric.get());
        } else {
            throw new RuntimeException("Metric not found with ID: " + id);
        }
    }

    public CampaignMetric getMetricById(Long id) {
        return campaignMetricRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metric not found with ID: " + id));
    }

    public List<CampaignMetric> getMetricsByCampaignId(Long campaignId) {
        return campaignMetricRepository.findByCampaignId(campaignId);
    }

    public CampaignMetric calculateMetricValue(Long id) {
        CampaignMetric metric = getMetricById(id);
        metric.calculateMetricValue();
        return campaignMetricRepository.save(metric);
    }

    public boolean isTargetAchieved(Long id) {
        CampaignMetric metric = getMetricById(id);
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
}