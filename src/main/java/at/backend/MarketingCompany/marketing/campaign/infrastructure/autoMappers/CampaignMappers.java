package at.backend.MarketingCompany.marketing.campaign.infrastructure.autoMappers;

import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.*;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.MarketingCampaignInsertDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.SuccessCriteriaDetailsDTO;
import at.backend.MarketingCompany.marketing.campaign.infrastructure.DTOs.TargetAudienceDetailsDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: HANDLE LEFT FIELDS
@Component
public class CampaignMappers {
    public MarketingCampaign insertDTOToDomain(MarketingCampaignInsertDTO insertDTO) {
        return MarketingCampaign.builder()
                .name(insertDTO.getName())
                .description(insertDTO.getDescription())
                .period(new CampaignPeriod(insertDTO.getStartDate(), insertDTO.getEndDate()))
                .budget(new Budget(insertDTO.getBudget(), BigDecimal.ZERO))
                .type(insertDTO.getType())
                .targetAudience(toTargetAudience(insertDTO.getTargetAudience()))
                .successCriteria(toSuccessCriteria(insertDTO.getSuccessCriteria()))
                .build();
    }

    public TargetAudience toTargetAudience(TargetAudienceDetailsDTO dto) {
        return new TargetAudience(dto.description(), dto.demographics(), dto.geographicLocations(), dto.interests());
    }

    public SuccessCriteria toSuccessCriteria(SuccessCriteriaDetailsDTO dto) {
        return new SuccessCriteria(dto.description(), dto.metrics());
    }

    public MarketingCampaignModel domainToModel(MarketingCampaign domain) {
        return MarketingCampaignModel.builder()
                .id(domain.getId().getValue())
                .name(domain.getName())
                .description(domain.getDescription())
                .type(domain.getType())
                .budget(domain.getBudget().totalBudget())
                .status(domain.getStatus())
                .costToDate(domain.getBudget().costToDate())
                .successCriteria(domain.getSuccessCriteria().toString())
                .targetAudience(domain.getTargetAudience().toString())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }



    public MarketingCampaignDTO modelToDTO(MarketingCampaignModel model) {
        return MarketingCampaignDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .budget(model.getBudget())
                .costToDate(model.getCostToDate())
                .status(model.getStatus())
                .type(model.getType())
                .targetAudience(model.getTargetAudience())
                .successCriteria(model.getSuccessCriteria())
                .targets(model.getTargets())
                .build();
    }

    public MarketingCampaignDTO domainToDTO(MarketingCampaign domain) {
        return MarketingCampaignDTO.builder()
                .id(domain.getId().getValue())
                .name(domain.getName())
                .description(domain.getDescription())
                .startDate(domain.getPeriod().startDate())
                .endDate(domain.getPeriod().endDate())
                .budget(domain.getBudget().totalBudget())
                .costToDate(domain.getBudget().costToDate())
                .status(domain.getStatus())
                .type(domain.getType())
                .targetAudience(domain.getTargetAudience().toString())
                .successCriteria(domain.getSuccessCriteria().toString())
                //.targets(domain.getTargets())
                .build();
    }

    public MarketingCampaign modelToDomain(MarketingCampaignModel model) {
        return MarketingCampaign.builder()
                .id(CampaignId.of(model.getId()))
                .name(model.getName())
                .description(model.getDescription())
                .period(new CampaignPeriod(model.getStartDate(), model.getEndDate()))
                .budget(new Budget(model.getBudget(), model.getCostToDate()))
                .status(model.getStatus())
                .type(model.getType())
                .targetAudience(targetAudienceFromString(model.getTargetAudience()))
                .successCriteria(successCriteriaFromString(model.getSuccessCriteria()))
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }

    private TargetAudience targetAudienceFromString(String targetAudienceStr) {
        return new TargetAudience("Parsed Description", Set.of("Parsed Demographics"), Set.of("Parsed Locations"), Set.of("Parsed Interests"));
    }

    private SuccessCriteria successCriteriaFromString(String successCriteriaStr) {
        return new SuccessCriteria("Parsed Description", Map.of("Parsed Metric", 0.0));
    }

}
