package at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class ConversionSummaryDTO {
    private UUID campaignId;
    private int conversionCount;
    private double totalConversionValue;
    private double averageConversionValue;

}
