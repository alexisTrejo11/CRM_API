package at.backend.MarketingCompany.marketing.attribution.api.service;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.AttributionModel;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.AttributionPercentage;
import at.backend.MarketingCompany.marketing.attribution.domain.CampaignAttribution;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AttributionCalculatorImpl implements AttributionCalculator {

    @Override
    public CampaignAttribution initialCalculation(CampaignAttribution attribution) {
        AttributionPercentage initialPercentage = new  AttributionPercentage(BigDecimal.valueOf(100));
        return CampaignAttribution.builder()
                .percentage(initialPercentage)
                .revenue(attribution.getRevenue())
                .timeline(attribution.getTimeline())
                .id(attribution.getId())
                .dealId(attribution.getDealId())
                .campaignId(attribution.getCampaignId())
                .createdAt(attribution.getCreatedAt())
                .updatedAt(attribution.getUpdatedAt())
                .build();
    }

    @Override
    public CampaignAttribution recalculateForModel(CampaignAttribution attribution) {
        AttributionPercentage recalculatedPercentage = CampaignAttribution.calculatePercentageForModel(attribution.getModel());
        return CampaignAttribution.builder()
                .percentage(recalculatedPercentage)
                .revenue(attribution.getRevenue())
                .timeline(attribution.getTimeline())
                .id(attribution.getId())
                .dealId(attribution.getDealId())
                .campaignId(attribution.getCampaignId())
                .createdAt(attribution.getCreatedAt())
                .updatedAt(attribution.getUpdatedAt())
                .build();
    }

    @Override
    public CampaignAttribution adjustForNewTouch(CampaignAttribution attribution) {
        int touchCount = attribution.getTimeline().getTouchCount();
        BigDecimal adjustedPercentageValue = BigDecimal.valueOf(100).divide(BigDecimal.valueOf(touchCount), 2, BigDecimal.ROUND_HALF_UP);
        System.out.println("Touch Count: " + touchCount);

        AttributionPercentage adjustedPercentage = new AttributionPercentage(adjustedPercentageValue);

        return CampaignAttribution.builder()
                .percentage(adjustedPercentage)
                .revenue(attribution.getRevenue())
                .timeline(attribution.getTimeline())
                .id(attribution.getId())
                .dealId(attribution.getDealId())
                .campaignId(attribution.getCampaignId())
                .createdAt(attribution.getCreatedAt())
                .updatedAt(attribution.getUpdatedAt())
                .build();
    }

    @Override
    public CampaignAttributionDTO calculateRevenueDistribution(List<CampaignAttribution> attributions) {
        BigDecimal totalRevenue = attributions.stream()
                .map(attribution -> attribution.getRevenue().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CampaignAttributionDTO> distribution = attributions.stream()
                .map(attribution -> CampaignAttributionDTO.builder()
                        .id(attribution.getId().getValue())
                        .dealId(attribution.getDealId().getValue())
                        .campaignId(attribution.getCampaignId().getValue())
                        .attributionPercentage(attribution.getPercentage().value())
                        .attributedRevenue(attribution.getRevenue().value())
                        .build())
                .toList();

        return CampaignAttributionDTO.builder()
                .attributedRevenue(totalRevenue)
                .distribution(distribution)
                .build();
    }

}
