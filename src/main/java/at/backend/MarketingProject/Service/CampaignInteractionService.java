package at.backend.MarketingProject.Service;

import at.backend.CRM.Repository.CustomerRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignInteractionService {

    private final CampaignInteractionRepository campaignInteractionRepository;
    private final MarketingCampaignRepository marketingCampaignRepository;
    private final CustomerRepository customerRepository;

    public CampaignInteraction createInteraction(Long campaignId, Long customerId, CampaignInteraction interaction) {
        validateInteraction(interaction);
        MarketingCampaign campaign = marketingCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + campaignId));
        Customer customer = getCustomerById(customerId);
        interaction.setCampaign(campaign);
        interaction.setCustomer(customer);
        return campaignInteractionRepository.save(interaction);
    }

    public CampaignInteraction updateInteraction(Long id, CampaignInteraction updatedInteraction) {
        Optional<CampaignInteraction> existingInteraction = campaignInteractionRepository.findById(id);
        if (existingInteraction.isPresent()) {
            CampaignInteraction interaction = existingInteraction.get();
            interaction.setInteractionType(updatedInteraction.getInteractionType());
            interaction.setInteractionDate(updatedInteraction.getInteractionDate());
            interaction.setSourceChannel(updatedInteraction.getSourceChannel());
            interaction.setSourceMedium(updatedInteraction.getSourceMedium());
            interaction.setSourceCampaign(updatedInteraction.getSourceCampaign());
            interaction.setDeviceType(updatedInteraction.getDeviceType());
            interaction.setIpAddress(updatedInteraction.getIpAddress());
            interaction.setGeoLocation(updatedInteraction.getGeoLocation());
            interaction.setProperties(updatedInteraction.getProperties());
            interaction.setDetails(updatedInteraction.getDetails());
            interaction.setResultedDeal(updatedInteraction.getResultedDeal());
            interaction.setConversionValue(updatedInteraction.getConversionValue());
            return campaignInteractionRepository.save(interaction);
        } else {
            throw new RuntimeException("Interaction not found with ID: " + id);
        }
    }

    public void deleteInteraction(Long id) {
        Optional<CampaignInteraction> interaction = campaignInteractionRepository.findById(id);
        if (interaction.isPresent()) {
            campaignInteractionRepository.delete(interaction.get());
        } else {
            throw new RuntimeException("Interaction not found with ID: " + id);
        }
    }

    public CampaignInteraction getInteractionById(Long id) {
        return campaignInteractionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interaction not found with ID: " + id));
    }

    public List<CampaignInteraction> getInteractionsByCampaignId(Long campaignId) {
        return campaignInteractionRepository.findByCampaignId(campaignId);
    }

    public List<CampaignInteraction> getInteractionsByCustomerId(Long customerId) {
        return campaignInteractionRepository.findByCustomerId(customerId);
    }

    public CampaignInteraction updateInteractionProperties(Long id, Map<String, String> properties) {
        CampaignInteraction interaction = getInteractionById(id);
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

    private Customer getCustomerById(Long customerId) {
         return customerRepository.findById(customerId)
                .orElseThrow(() -> new UnsupportedOperationException("Customer fetching logic needs to be implemented"));
    }
}