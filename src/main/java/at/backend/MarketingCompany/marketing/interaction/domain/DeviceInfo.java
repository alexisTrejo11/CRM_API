package at.backend.MarketingCompany.marketing.interaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInfo {
    private String deviceType;
    private String ipAddress;
}
