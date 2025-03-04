package at.backend.MarketingCompany.marketing.attribution.infrastructure.automappers;

import at.backend.MarketingCompany.crm.deal.domain.Deal;
import at.backend.MarketingCompany.marketing.attribution.api.repository.CampaignAttributionModel;
import at.backend.MarketingCompany.marketing.attribution.domain.CampaignAttribution;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.*;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionDTO;
import at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs.CampaignAttributionInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;

import java.time.LocalDateTime;

public class AttributionMappers {

    public CampaignAttribution insertDTOToDomain(CampaignAttributionInsertDTO insertDTO) {
        return CampaignAttribution.create(
                        DealId.of(insertDTO.getDealId()),
                        CampaignId.of(insertDTO.getCampaignId()),
                        insertDTO.getAttributionModel(),
                        TouchTimeline.builder()
                                .firstTouch(LocalDateTime.now())
                                .lastTouch(LocalDateTime.now())
                                .touchCount(insertDTO.getTouchCount() != null ? insertDTO.getTouchCount() : 0)
                                .build()
                ).addTouch(LocalDateTime.now())
                .recalculateForModel(insertDTO.getAttributionModel());
    }

    public CampaignAttributionDTO domainToDTO(CampaignAttribution domain) {
        return CampaignAttributionDTO.builder()
                .id(domain.getId().getValue())
                .dealId(domain.getDealId().getValue())
                .campaignId(domain.getCampaignId().getValue())
                .attributionModel(domain.getModel())
                .attributionPercentage(domain.getPercentage().value())
                .attributedRevenue(domain.getRevenue().value())
                .firstTouchDate(domain.getTimeline().getFirstTouch())
                .lastTouchDate(domain.getTimeline().getLastTouch())
                .touchCount(domain.getTimeline().getTouchCount())
                .build();
    }


    public CampaignAttributionModel domainToModel(CampaignAttribution domain) {
        return CampaignAttributionModel.builder()
                .id(domain.getId().getValue())
                //.deal(new Deal(domain.getDealId().getValue()))
                //.campaign(new MarketingCampaignModel(domain.getCampaignId().getValue()))
                .attributionModel(domain.getModel())
                .attributionPercentage(domain.getPercentage().value())
                .attributedRevenue(domain.getRevenue().value())
                .firstTouchDate(domain.getTimeline().getFirstTouch())
                .lastTouchDate(domain.getTimeline().getLastTouch())
                .touchCount(domain.getTimeline().getTouchCount())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public CampaignAttributionDTO modelToDTO(CampaignAttributionModel model) {
        return CampaignAttributionDTO.builder()
                .id(model.getId())
                .dealId(model.getDeal().getId())
                .campaignId(model.getCampaign().getId())
                .attributionModel(model.getAttributionModel())
                .attributionPercentage(model.getAttributionPercentage())
                .attributedRevenue(model.getAttributedRevenue())
                .firstTouchDate(model.getFirstTouchDate())
                .lastTouchDate(model.getLastTouchDate())
                .touchCount(model.getTouchCount())
                .build();
    }

    public CampaignAttribution modelToDomain(CampaignAttributionModel model) {
        return CampaignAttribution.builder()
                .id(AttributionId.of(model.getId()))
                .dealId(DealId.of(model.getDeal().getId()))
                .campaignId(CampaignId.of(model.getCampaign().getId()))
                .model(model.getAttributionModel())
                .percentage(new AttributionPercentage(model.getAttributionPercentage()))
                .revenue(new AttributedRevenue(model.getAttributedRevenue()))
                .timeline(TouchTimeline.builder()
                        .firstTouch(model.getFirstTouchDate())
                        .lastTouch(model.getLastTouchDate())
                        .touchCount(model.getTouchCount())
                        .build())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }
}

