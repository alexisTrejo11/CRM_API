package at.backend.MarketingCompany.marketing.interaction.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InteractionSource {
    private String channel;
    private String medium;
    private String campaignName;
}

