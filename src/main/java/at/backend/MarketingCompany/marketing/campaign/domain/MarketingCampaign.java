package at.backend.MarketingCompany.marketing.campaign.domain;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignStatus;
import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.CampaignType;
import at.backend.MarketingCompany.marketing.activity.api.repository.CampaignActivityModel;
import at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers.CampaignId;
import at.backend.MarketingCompany.marketing.campaign.domain.Exceptions.CampaignException;
import at.backend.MarketingCompany.marketing.campaign.domain.HelperClasses.*;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CampaignInteractionModel;
import at.backend.MarketingCompany.marketing.metric.api.repository.CampaignMetricModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MarketingCampaign {
    private final CampaignId id;
    private String name;
    private String description;
    private CampaignPeriod period;
    private Budget budget;
    private CampaignStatus status;
    private CampaignType type;
    private TargetAudience targetAudience;
    private SuccessCriteria successCriteria;

    private Set<CampaignTarget> targets = new HashSet<>();
    private List<CampaignInteractionModel> interactions = new ArrayList<>();
    private List<CampaignMetricModel> metrics = new ArrayList<>();
    private List<CampaignActivityModel> activities = new ArrayList<>();
    private Set<Long> relatedDeals = new HashSet<>();
    private Set<Long> targetSegments = new HashSet<>();

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MarketingCampaign(
            String name,
            String description,
            CampaignPeriod period,
            Budget budget,
            CampaignType type,
            TargetAudience targetAudience,
            SuccessCriteria successCriteria
    ) {
        this.id = CampaignId.generate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        setName(name);
        setDescription(description);
        this.period = Objects.requireNonNull(period, "The campaign period is required");
        this.budget = Objects.requireNonNull(budget, "The budget is required");
        this.status = CampaignStatus.DRAFT;
        this.type = Objects.requireNonNull(type, "The campaign type is required");
        this.targetAudience = Objects.requireNonNull(targetAudience, "The target audience is required");
        this.successCriteria = Objects.requireNonNull(successCriteria, "The success criteria are required");

        validateInitialState();
    }



    private void validateInitialState() {
        if (period.startDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new CampaignException("Campaigns must be created at least 1 day in advance");
        }
    }

    public void startCampaign() {
        if (status != CampaignStatus.DRAFT) {
            throw new CampaignException("Only draft campaigns can be started");
        }
        if (period.startDate().isAfter(LocalDate.now())) {
            throw new CampaignException("A campaign cannot be started before its scheduled start date");
        }
        this.status = CampaignStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void completeCampaign() {
        if (status != CampaignStatus.ACTIVE) {
            throw new CampaignException("Only active campaigns can be completed");
        }
        if (period.endDate() == null || LocalDate.now().isBefore(period.endDate())) {
            throw new CampaignException("A campaign cannot be completed before its end date");
        }
        this.status = CampaignStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void addInteraction(CampaignInteractionModel interaction) {
        if (!isActive()) {
            throw new CampaignException("Interactions can only be added to active campaigns");
        }
        this.interactions.add(interaction);
        this.updatedAt = LocalDateTime.now();
    }

    public void addCost(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CampaignException("The cost must be greater than zero");
        }
        Budget newBudget = this.budget.addCost(amount);
        if (newBudget.exceedsBudget()) {
            throw new CampaignException("The cost exceeds the allocated budget");
        }
        this.budget = newBudget;
        this.updatedAt = LocalDateTime.now();
    }

    public void addTarget(CampaignTarget target) {
        if (targets.stream().anyMatch(t -> t.metricName().equals(target.metricName()))) {
            throw new CampaignException("The metric already exists in the targets");
        }
        targets.add(target);
        updatedAt = LocalDateTime.now();
    }

    public void archive() {
        if (this.getStatus() == CampaignStatus.CANCELLED) {
            throw new CampaignException("Campaign is already archived");
        }

        if (this.getStatus() == CampaignStatus.ACTIVE) {
            throw new CampaignException("Cannot archive an active campaign");
        }

        if (this.getStatus() == CampaignStatus.DRAFT) {
            throw new CampaignException("Cannot archive a draft campaign");
        }

        updatedAt = LocalDateTime.now();
    }

    public double calculateROI() {
        if (budget.totalBudget() == null || budget.totalBudget().compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        BigDecimal totalRevenue = metrics.stream()
                .map(CampaignMetricModel::getRevenue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalRevenue.subtract(budget.totalBudget())
                .divide(budget.totalBudget(), 2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new CampaignException("The campaign name is required");
        }
        if (name.length() > 100) {
            throw new CampaignException("The name cannot exceed 100 characters");
        }
        this.name = name.trim();
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 1000) {
            throw new CampaignException("The description cannot exceed 1000 characters");
        }
        this.description = description != null ? description.trim() : null;
    }

    public boolean isActive() {
        return status == CampaignStatus.ACTIVE;
    }

    public boolean isCompleted() {
        return status == CampaignStatus.COMPLETED;
    }
}