package at.backend.MarketingCompany.marketing.interaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GeoLocation {
    private String country;
    private String region;
    private String city;
}
