package at.backend.MarketingCompany.marketing.interaction.api.service;

import at.backend.MarketingCompany.customer.api.repository.CustomerRepository;
import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.autoMappers.CampaignInteractionMappers;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionDTO;
import at.backend.MarketingCompany.marketing.interaction.infrastructure.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CampaignInteractionModel;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignModel;
import at.backend.MarketingCompany.customer.domain.Customer;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CampaignInteractionRepository;
import at.backend.MarketingCompany.marketing.campaign.api.repository.MarketingCampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CampaignInteractionServiceImpl implements CampaignInteractionService {

    private final CampaignInteractionRepository campaignInteractionRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CustomerRepository customerRepository;
    private final CampaignInteractionMappers campaignInteractionMappers;

    @Override
    public CampaignInteractionDTO create(CampaignInteractionInsertDTO insertDTO) {
        CampaignInteractionModel interaction = campaignInteractionMappers.inputToEntity(insertDTO);

        validate(insertDTO);

        fetchInteractionRelationships(interaction, insertDTO.getCampaignId(), insertDTO.getCustomerId());

        campaignInteractionRepository.save(interaction);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    @Override
    public CampaignInteractionDTO update(Long id, CampaignInteractionInsertDTO insertDTO) {
        CampaignInteractionModel existingInteraction = getInteraction(id);

        campaignInteractionMappers.updateEntity(existingInteraction, insertDTO);

        campaignInteractionRepository.save(existingInteraction);

        return campaignInteractionMappers.entityToDTO(existingInteraction);
    }

    @Override
    public void delete(Object id) {
        CampaignInteractionModel interaction = getInteraction(id);

        campaignInteractionRepository.delete(interaction);
    }

    @Override
    public Page<CampaignInteractionDTO> getAll(Pageable pageable) {
        return campaignInteractionRepository.findAll(pageable).map(campaignInteractionMappers::entityToDTO);
    }

    @Override
    public CampaignInteractionDTO getById(Object id) {
        CampaignInteractionModel interaction = getInteraction(id);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    @Override
    public List<CampaignInteractionDTO> getInteractionsByCampaignId(Long campaignId) {
        return campaignInteractionRepository.findByCampaignId(campaignId)
                .stream()
                .map(campaignInteractionMappers::entityToDTO)
                .toList();
    }

    @Override
    public List<CampaignInteractionDTO> getInteractionsByCustomerId(Long customerId) {
        return campaignInteractionRepository.findByCustomerId(customerId)
                .stream()
                .map(campaignInteractionMappers::entityToDTO)
                .toList();
    }

    @Override
    public CampaignInteractionDTO updateInteractionProperties(Long id, Map<String, String> properties) {
        CampaignInteractionModel interaction = getInteraction(id);

        interaction.getProperties().putAll(properties);

        campaignInteractionRepository.save(interaction);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    public Double calculateTotalConversionValue(Long campaignId) {
        List<CampaignInteractionModel> interactions = campaignInteractionRepository.findByCampaignId(campaignId);

        return interactions.stream()
                .map(CampaignInteractionModel::getConversionValue)
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
    }

    @Override
    public void validate(CampaignInteractionInsertDTO insertDTO) {
        if (insertDTO.getInteractionType() == null) {
            throw new InvalidInputException("Interaction type cannot be null");
        }
        if (insertDTO.getInteractionDate() == null) {
            throw new InvalidInputException("Interaction date cannot be null");
        }
        if (insertDTO.getConversionValue() != null && insertDTO.getConversionValue() < 0) {
            throw new InvalidInputException("Conversion value must be greater than or equal to zero");
        }
    }

    private CampaignInteractionModel getInteraction(Long id) {
        return campaignInteractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interaction not found with ID: " + id));
    }

    private Customer getCustomerById(Long customerId) {
         return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
    }

    private MarketingCampaignModel getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }

    private void fetchInteractionRelationships(CampaignInteractionModel interaction, Long campaignId, Long customerId) {
        Customer customer = getCustomerById(customerId);
        MarketingCampaignModel campaign = getCampaign(campaignId);
        interaction.setCampaign(campaign);
        interaction.setCustomer(customer);
    }
}