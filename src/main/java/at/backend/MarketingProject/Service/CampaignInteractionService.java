package at.backend.MarketingProject.Service;

import at.backend.CRM.Repository.CustomerRepository;
import at.backend.MarketingProject.AutoMappers.CampaignInteractionMappers;
import at.backend.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.MarketingProject.Models.CampaignInteraction;
import at.backend.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.Models.Customer;
import at.backend.MarketingProject.Repository.CampaignInteractionRepository;
import at.backend.MarketingProject.Repository.MarketingCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CampaignInteractionService {

    private final CampaignInteractionRepository campaignInteractionRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CustomerRepository customerRepository;
    private final CampaignInteractionMappers campaignInteractionMappers;

    public CampaignInteractionDTO createInteraction(CampaignInteractionInsertDTO insertDTO) {
        CampaignInteraction interaction = campaignInteractionMappers.inputToEntity(insertDTO);

        validateInteraction(interaction);

        fetchInteractionRelationships(interaction, insertDTO.getCampaignId(), insertDTO.getCustomerId());

        campaignInteractionRepository.save(interaction);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    public CampaignInteractionDTO updateInteraction(Long id, CampaignInteractionInsertDTO insertDTO) {
        CampaignInteraction existingInteraction = getInteraction(id);

        campaignInteractionMappers.updateEntity(existingInteraction, insertDTO);

        campaignInteractionRepository.save(existingInteraction);

        return campaignInteractionMappers.entityToDTO(existingInteraction);
    }

    public void deleteInteraction(Long id) {
        CampaignInteraction interaction = getInteraction(id);

        campaignInteractionRepository.delete(interaction);
    }

    public CampaignInteractionDTO getInteractionById(Long id) {
        CampaignInteraction interaction = getInteraction(id);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    public List<CampaignInteraction> getInteractionsByCampaignId(Long campaignId) {
        return campaignInteractionRepository.findByCampaignId(campaignId);
    }

    public List<CampaignInteraction> getInteractionsByCustomerId(Long customerId) {
        return campaignInteractionRepository.findByCustomerId(customerId);
    }

    public CampaignInteraction updateInteractionProperties(Long id, Map<String, String> properties) {
        CampaignInteraction interaction = getInteraction(id);

        interaction.getProperties().putAll(properties);

        return campaignInteractionRepository.save(interaction);
    }

    public Double calculateTotalConversionValue(Long campaignId) {
        List<CampaignInteraction> interactions = campaignInteractionRepository.findByCampaignId(campaignId);

        return interactions.stream()
                .map(CampaignInteraction::getConversionValue)
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
    }

    private void validateInteraction(CampaignInteraction interaction) {
        if (interaction.getInteractionType() == null) {
            throw new IllegalArgumentException("Interaction type cannot be null");
        }
        if (interaction.getInteractionDate() == null) {
            throw new IllegalArgumentException("Interaction date cannot be null");
        }
        if (interaction.getConversionValue() != null && interaction.getConversionValue() < 0) {
            throw new IllegalArgumentException("Conversion value must be greater than or equal to zero");
        }
    }

    private CampaignInteraction getInteraction(Long id) {
        return campaignInteractionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interaction not found with ID: " + id));
    }

    private Customer getCustomerById(Long customerId) {
         return customerRepository.findById(customerId)
                .orElseThrow(() -> new UnsupportedOperationException("Customer fetching logic needs to be implemented"));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignID));
    }

    private void fetchInteractionRelationships(CampaignInteraction interaction, Long campaignId, Long customerId) {
        Customer customer = getCustomerById(customerId);
        MarketingCampaign campaign = getCampaign(campaignId);
        interaction.setCampaign(campaign);
        interaction.setCustomer(customer);
    }
}