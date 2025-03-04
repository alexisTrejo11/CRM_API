package at.backend.MarketingCompany.marketing.metric.api.repository;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.MetricType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampaignMetricRepository extends JpaRepository<CampaignMetricModel, UUID> {

    List<CampaignMetricModel> findByCampaignId(UUID campaignId);

    List<CampaignMetricModel> findByCampaignIdAndType(Long campaignId, MetricType type);

    Optional<CampaignMetricModel> findByCampaignIdAndName(Long campaignId, String name);

    @Query("SELECT cm FROM CampaignMetricModel cm WHERE cm.campaign.id = :campaignId AND cm.value >= cm.targetValue")
    List<CampaignMetricModel> findAchievedMetricsByCampaignId(@Param("campaignId") Long campaignId);

    @Query("SELECT cm FROM CampaignMetricModel cm WHERE cm.campaign.id = :campaignId " +
            "AND cm.targetValue IS NOT NULL AND cm.value < cm.targetValue")
    List<CampaignMetricModel> findUnderperformingMetricsByCampaignId(@Param("campaignId") Long campaignId);

    @Query("SELECT AVG(cm.value) FROM CampaignMetricModel cm WHERE cm.name = :metricName " +
            "AND cm.campaign.type = :campaignType")
    Optional<BigDecimal> findAverageMetricValueByNameAndCampaignType(
            @Param("metricName") String metricName,
            @Param("campaignType") String campaignType);

    @Query("SELECT cm FROM CampaignMetricModel cm WHERE cm.automated = true " +
            "AND (cm.lastCalculated IS NULL OR cm.lastCalculated < :thresholdDate)")
    List<CampaignMetricModel> findMetricsNeedingRecalculation(@Param("thresholdDate") java.time.LocalDateTime thresholdDate);
}