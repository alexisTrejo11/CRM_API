package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CRM.Repository.DealRepository;
import at.backend.CRM.CommonClasses.Exceptions.BusinessLogicException;
import at.backend.CRM.CommonClasses.Exceptions.InvalidInputException;
import at.backend.CRM.MarketingProject.AutoMappers.CampaignAttributionMappers;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignAttributionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignAttribution;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.CRM.Models.Deal;
import at.backend.CRM.MarketingProject.Repository.CampaignAttributionRepository;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignAttributionServiceImpl implements CampaignAttributionService {

    private final CampaignAttributionRepository campaignAttributionRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final DealRepository dealRepository;
    private final CampaignAttributionMappers campaignAttributionMappers;

    @Override
    public Page<CampaignAttributionDTO> getAll(Pageable pageable) {
        return campaignAttributionRepository.findAll(pageable).map(campaignAttributionMappers::entityToDTO);
    }

    @Override
    public CampaignAttributionDTO getById(Long id) {
        CampaignAttribution existingAttribution = getAttribution(id);

        return campaignAttributionMappers.entityToDTO(existingAttribution);
    }

    @Override
    public CampaignAttributionDTO create(CampaignAttributionInsertDTO insertDTO) {
        CampaignAttribution attribution = campaignAttributionMappers.inputToEntity(insertDTO);

        validate(insertDTO);
        fetchAttributionRelationship(attribution, insertDTO);

        campaignAttributionRepository.save(attribution);

        return campaignAttributionMappers.entityToDTO(attribution);
    }

    @Override
    public CampaignAttributionDTO update(Long id, CampaignAttributionInsertDTO input) {
        CampaignAttribution existingAttribution = getAttribution(id);

        campaignAttributionMappers.updateEntity(existingAttribution, input);
        campaignAttributionRepository.save(existingAttribution);

        return campaignAttributionMappers.entityToDTO(existingAttribution);
    }

    @Override
    public void delete(Long id) {
        CampaignAttribution existingAttribution = getAttribution(id);

        campaignAttributionRepository.delete(existingAttribution);
    }

    @Override
    public List<CampaignAttributionDTO> getAttributionsByCampaignId(Long campaignId) {
        return campaignAttributionRepository.findByCampaignId(campaignId)
                .stream()
                .map(campaignAttributionMappers::entityToDTO)
                .toList();
    }

    @Override
    public List<CampaignAttributionDTO> getAttributionsByDealId(Long dealId) {
        return campaignAttributionRepository.findByDealId(dealId)
                .stream()
                .map(campaignAttributionMappers::entityToDTO)
                .toList();
    }

    public BigDecimal calculateTotalAttributedRevenue(Long campaignId) {
        List<CampaignAttribution> attributions = campaignAttributionRepository.findByCampaignId(campaignId);
        return attributions
                .stream()
                .map(CampaignAttribution::getAttributedRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void validate(CampaignAttributionInsertDTO insertDTO) {
        if (insertDTO.getAttributionModel() == null) {
            throw new InvalidInputException("Attribution model cannot be null");
        }
        if (insertDTO.getAttributionPercentage() == null || insertDTO.getAttributionPercentage().compareTo(BigDecimal.ZERO) < 0 ||
                insertDTO.getAttributionPercentage().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new BusinessLogicException("Attribution percentage must be between 0 and 100");
        }
        if (insertDTO.getAttributedRevenue() == null || insertDTO.getAttributedRevenue().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessLogicException("Attributed revenue must be greater than or equal to zero");
        }
    }

    private Deal getDeal(Long dealID){
        return dealRepository.findById(dealID)
                .orElseThrow(() -> new EntityNotFoundException("Deal not found with ID: " + dealID));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }

    private void fetchAttributionRelationship(CampaignAttribution attribution, CampaignAttributionInsertDTO insertDTO){
        MarketingCampaign campaign = getCampaign(insertDTO.getCampaignId());
        attribution.setCampaign(campaign);

        Deal deal = getDeal(insertDTO.getDealId());
        attribution.setDeal(deal);
    }

    private CampaignAttribution getAttribution(Long id) {
        return campaignAttributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attribution not found with ID: " + id));
    }
}
