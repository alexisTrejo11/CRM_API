package at.backend.MarketingCompany.MarketingCampaing.controller;

import at.backend.MarketingCompany.marketing.metric.api.service.CampaignMetricService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampaignMetricControllerTestConfig {

    @Bean
    public CampaignMetricService campaignMetricService() {
        return Mockito.mock(CampaignMetricService.class);
    }
}
