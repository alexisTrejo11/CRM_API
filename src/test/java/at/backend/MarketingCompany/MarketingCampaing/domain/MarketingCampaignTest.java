package at.backend.MarketingCompany.MarketingCampaing.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignType;
import at.backend.MarketingCompany.marketing.campaign.domain.Exceptions.CampaignException;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.*;
import at.backend.MarketingCompany.marketing.campaign.domain.MarketingCampaign;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CampaignInteractionModel;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetricModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MarketingCampaignTest {

    private static final String VALID_NAME = "Valid Campaign";
    private static final String VALID_DESCRIPTION = "Valid Description";
    private static final CampaignType VALID_TYPE = CampaignType.EMAIL;
    private static final TargetAudience VALID_AUDIENCE = TargetAudience.builder()
            .description("US young professionals")
            .demographics(Set.of("25-34"))
            .geographicLocations(Set.of("United States"))
            .interests(Set.of("Tech", "E-commerce"))
            .build();

    private static final SuccessCriteria VALID_CRITERIA = SuccessCriteria.builder()
            .description("10% ROI target")
            .metrics(Map.of("targetROI", 0.1))
            .build();
    private CampaignPeriod validPeriod;
    private Budget validBudget;

    @BeforeEach
    void setUp() {
        validPeriod = new CampaignPeriod(LocalDate.now().plusDays(2), LocalDate.now().plusDays(10));
        validBudget = new Budget(new BigDecimal(1000), BigDecimal.ZERO);
    }

    @Nested
    class ConstructorTests {

        @Test
        void constructor_WhenStartDateIsToday_ThrowsException() {
            CampaignPeriod invalidPeriod = new CampaignPeriod(LocalDate.now(), LocalDate.now().plusDays(1));
            assertThatThrownBy(() -> new MarketingCampaign(
                    VALID_NAME, VALID_DESCRIPTION, invalidPeriod, validBudget, VALID_TYPE, VALID_AUDIENCE, VALID_CRITERIA
            )).isInstanceOf(CampaignException.class)
                    .hasMessageContaining("at least 1 day in advance");
        }

        @Test
        void constructor_SetsDefaultValues() {
            MarketingCampaign campaign = new MarketingCampaign(
                    VALID_NAME, VALID_DESCRIPTION, validPeriod, validBudget, VALID_TYPE, VALID_AUDIENCE, VALID_CRITERIA
            );
            assertThat(campaign.getStatus()).isEqualTo(CampaignStatus.DRAFT);
            assertThat(campaign.getCreatedAt()).isNotNull();
            assertThat(campaign.getUpdatedAt()).isNotNull();
        }
    }

    @Nested
    class StartCampaignTests {

        @Test
        void startCampaign_WhenNotDraft_ThrowsException() {
            MarketingCampaign campaign = buildCampaignWithStatus(CampaignStatus.ACTIVE);
            assertThatThrownBy(campaign::startCampaign)
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("Only draft campaigns can be started");
        }

        @Test
        void startCampaign_WhenStartDateInFuture_ThrowsException() {
            MarketingCampaign campaign = buildCampaignWithPeriod(
                    new CampaignPeriod(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5))
            );
            assertThatThrownBy(campaign::startCampaign)
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("before its scheduled start date");
        }

        @Test
        void startCampaign_Successful() {
            MarketingCampaign campaign = buildActiveCampaign();

            assertThat(campaign.getStatus()).isEqualTo(CampaignStatus.ACTIVE);
        }
    }

    @Nested
    class CompleteCampaignTests {

        @Test
        void completeCampaign_WhenNotActive_ThrowsException() {
            MarketingCampaign campaign = buildCampaignWithStatus(CampaignStatus.DRAFT);
            assertThatThrownBy(campaign::completeCampaign)
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("Only active campaigns can be completed");
        }

        @Test
        void completeCampaign_BeforeEndDate_ThrowsException() {
            MarketingCampaign campaign = buildActiveCampaign();

            assertThatThrownBy(campaign::completeCampaign)
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("before its end date");
        }

        @Test
        void completeCampaign_Successful() {
            MarketingCampaign campaign = buildActiveCampaign();

            campaign.setPeriod(new CampaignPeriod(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1)));

            campaign.completeCampaign();
            assertThat(campaign.getStatus()).isEqualTo(CampaignStatus.COMPLETED);
        }
    }

    @Nested
    class AddInteractionTests {

        @Test
        void addInteraction_WhenNotActive_ThrowsException() {
            MarketingCampaign campaign = buildCampaignWithStatus(CampaignStatus.DRAFT);
            CampaignInteractionModel interaction = mock(CampaignInteractionModel.class);
            assertThatThrownBy(() -> campaign.addInteraction(interaction))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("active campaigns");
        }

        @Test
        void addInteraction_Successful() {
            MarketingCampaign campaign = buildActiveCampaign();
            CampaignInteractionModel interaction = mock(CampaignInteractionModel.class);
            campaign.addInteraction(interaction);
            assertThat(campaign.getInteractions()).contains(interaction);
        }
    }

    @Nested
    class AddCostTests {

        @Test
        void addCost_NonPositiveAmount_ThrowsException() {
            MarketingCampaign campaign = buildCampaign();
            assertThatThrownBy(() -> campaign.addCost(BigDecimal.ZERO))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("greater than zero");
        }

        @Test
        void addCost_ExceedsBudget_ThrowsException() {
            MarketingCampaign campaign = buildCampaignWithBudget(new Budget(new BigDecimal(100), BigDecimal.ZERO));
            assertThatThrownBy(() -> campaign.addCost(new BigDecimal(101)))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("exceeds the allocated budget");
        }

        @Test
        void addCost_Successful() {
            MarketingCampaign campaign = buildCampaign();
            campaign.addCost(new BigDecimal(50));
            assertThat(campaign.getBudget().getSpentAmount()).isEqualByComparingTo(new BigDecimal(50));
        }
    }

    @Nested
    class AddTargetTests {

        @Test
        void addTarget_DuplicateMetric_ThrowsException() {
            MarketingCampaign campaign = buildCampaign();
            CampaignTarget target1 = new CampaignTarget("metric1", 100);
            CampaignTarget target2 = new CampaignTarget("metric1", 200);
            campaign.addTarget(target1);
            assertThatThrownBy(() -> campaign.addTarget(target2))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("already exists");
        }

        @Test
        void addTarget_Successful() {
            MarketingCampaign campaign = buildCampaign();
            CampaignTarget target = new CampaignTarget("metric1", 100);
            campaign.addTarget(target);
            assertThat(campaign.getTargets()).contains(target);
        }
    }

    @Nested
    class ArchiveTests {

        @Test
        void archive_WhenActive_ThrowsException() {
            MarketingCampaign campaign = buildActiveCampaign();
            assertThatThrownBy(campaign::archive)
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("active campaign");
        }

        @Test
        void archive_WhenDraft_ThrowsException() {
            MarketingCampaign campaign = buildCampaignWithStatus(CampaignStatus.DRAFT);
            assertThatThrownBy(campaign::archive)
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("draft campaign");
        }

        @Test
        void archive_Successful() {
            MarketingCampaign campaign = buildCampaignWithStatus(CampaignStatus.COMPLETED);
            campaign.archive();
            assertThat(campaign.getStatus()).isEqualTo(CampaignStatus.CANCELLED);
        }
    }

    @Nested
    class CalculateROITests {

        @Test
        void calculateROI_ZeroBudget_ReturnsZero() {
            MarketingCampaign campaign = buildCampaignWithBudget(new Budget(BigDecimal.ZERO, BigDecimal.ZERO));
            assertThat(campaign.calculateROI()).isZero();
        }

        @Test
        void calculateROI_ValidRevenue_ReturnsCorrectROI() {
            MarketingCampaign campaign = buildCampaign();
            CampaignMetricModel metric = new CampaignMetricModel();
            metric.setRevenue(new BigDecimal(1500));
            campaign.getMetrics().add(metric);
            assertThat(campaign.calculateROI()).isEqualTo(0.5); // (1500-1000)/1000 = 0.5
        }
    }

    @Nested
    class SetNameAndDescriptionTests {

        @Test
        void setName_EmptyName_ThrowsException() {
            MarketingCampaign campaign = buildCampaign();
            assertThatThrownBy(() -> campaign.setName(""))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("campaign name is required");
        }

        @Test
        void setName_TooLongName_ThrowsException() {
            MarketingCampaign campaign = buildCampaign();
            assertThatThrownBy(() -> campaign.setName("a".repeat(101)))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("100 characters");
        }

        @Test
        void setDescription_TooLongDescription_ThrowsException() {
            MarketingCampaign campaign = buildCampaign();
            assertThatThrownBy(() -> campaign.setDescription("a".repeat(1001)))
                    .isInstanceOf(CampaignException.class)
                    .hasMessageContaining("1000 characters");
        }
    }

    private MarketingCampaign buildCampaign() {
        return new MarketingCampaign(
                VALID_NAME, VALID_DESCRIPTION, validPeriod, validBudget, VALID_TYPE, VALID_AUDIENCE, VALID_CRITERIA
        );
    }

    private MarketingCampaign buildCampaignWithStatus(CampaignStatus status) {
        MarketingCampaign campaign = buildCampaign();
        campaign.setStatus(status);
        return campaign;
    }

    private MarketingCampaign buildCampaignWithPeriod(CampaignPeriod period) {
        return new MarketingCampaign(
                VALID_NAME, VALID_DESCRIPTION, period, validBudget, VALID_TYPE, VALID_AUDIENCE, VALID_CRITERIA
        );
    }

    private MarketingCampaign buildCampaignWithBudget(Budget budget) {
        return new MarketingCampaign(
                VALID_NAME, VALID_DESCRIPTION, validPeriod, budget, VALID_TYPE, VALID_AUDIENCE, VALID_CRITERIA
        );
    }

    private MarketingCampaign buildActiveCampaign() {
        MarketingCampaign campaign = buildCampaignWithPeriod(
                new CampaignPeriod(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5))
        );

        campaign.setPeriod(new CampaignPeriod(LocalDate.now().minusDays(1), LocalDate.now().plusDays(5)));

        campaign.startCampaign();
        return campaign;
    }
}