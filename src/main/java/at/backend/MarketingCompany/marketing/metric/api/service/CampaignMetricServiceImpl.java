package at.backend.MarketingCompany.marketing.metric.api.service;

import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers.CampaignMappers;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetricModel;
import at.backend.MarketingCompany.marketing.metric.domain.CampaignMetric;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricDTO;
import at.backend.MarketingCompany.marketing.metric.infrastructure.DTOs.CampaignMetricInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetricRepository;
import at.backend.MarketingCompany.marketing.metric.infrastructure.autoMappers.MetricMappers;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignMetricServiceImpl implements CampaignMetricService {

    private final CampaignMetricRepository campaignMetricRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final MetricMappers metricMappers;
    private final CampaignMappers campaignMappers;

    @Override
    @Transactional
    public CampaignMetricDTO create(CampaignMetricInsertDTO insertDTO) {
        validate(insertDTO);

        MarketingCampaign campaign = getCampaign(insertDTO.getCampaignId());

        CampaignMetric metric = metricMappers.insertDTOToDomain(insertDTO);
        metric.setCampaign(campaign);

        validateMetricCompatibility(metric, campaign);
        save(metric);

        return metricMappers.domainToDTO(metric);
    }

    @Override
    @Transactional
    public CampaignMetricDTO update(UUID id, CampaignMetricInsertDTO updateDTO) {
        CampaignMetric existing = getMetric(id);
        validate(updateDTO);

        //metricMappers.updateDomainFromDTO(existing, updateDTO);
        validateMetricCompatibility(existing, existing.getCampaign());

        save(existing);
        return metricMappers.domainToDTO(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CampaignMetric metric = getMetric(id);
        validateDeletionConditions(metric);
        campaignMetricRepository.deleteById(id);
    }

    @Override
    public Page<CampaignMetricDTO> getAll(Pageable pageable) {
        return campaignMetricRepository.findAll(pageable)
                .map(metricMappers::modelToDTO);
    }

    @Override
    public CampaignMetricDTO getById(UUID id) {
        return metricMappers.domainToDTO(getMetric(id));
    }

    @Override
    public List<CampaignMetricDTO> getMetricsByCampaignId(UUID campaignId) {
        return campaignMetricRepository.findByCampaignId(campaignId).stream()
                .map(metricMappers::modelToDTO)
                .toList();
    }

    @Override
    @Transactional
    public BigDecimal calculateCurrentPerformance(UUID id) {
        CampaignMetric metric = getMetric(id);
        BigDecimal performance = metric.calculatePerformanceRatio();
        save(metric);
        return performance;
    }

    @Override
    public boolean isTargetAchieved(UUID id) {
        return getMetric(id).isTargetAchieved();
    }

    public void validate(CampaignMetricInsertDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank())
            throw new InvalidInputException("Name is required");

        if (dto.getType() == null)
            throw new InvalidInputException("Metric type is required");

        if (dto.getTargetValue() == null || dto.getTargetValue().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidInputException("Invalid target value");

        if (dto.getType() == MetricType.PERCENTAGE && dto.getTargetValue().compareTo(BigDecimal.valueOf(100)) > 0)
            throw new InvalidInputException("Percentage cannot exceed 100%");
    }

    private void validateMetricCompatibility(CampaignMetric metric, MarketingCampaign campaign) {
        if (!campaign.isActive())
            throw new InvalidInputException("Cannot add metrics to inactive campaigns");

        if (campaign.getMetrics().stream()
                .anyMatch(m -> m.getName().equals(metric.getName())))
            throw new InvalidInputException("Metric name must be unique per campaign");
    }

    private void validateDeletionConditions(CampaignMetric metric) {
        if (metric.getCampaign().isCompleted())
            throw new InvalidInputException("Cannot delete metrics from completed campaigns");
    }

    private CampaignMetric getMetric(UUID id) {
        return campaignMetricRepository.findById(id)
                .map(metricMappers::modelToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Metric not found"));
    }

    private MarketingCampaign getCampaign(UUID campaignId) {
        return marketingCampaignRepository.findById(campaignId)
                .map(campaignMappers::modelToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
    }

    private void save(CampaignMetric metric) {
        CampaignMetricModel model = metricMappers.domainToModel(metric);
        campaignMetricRepository.save(model);
    }
}