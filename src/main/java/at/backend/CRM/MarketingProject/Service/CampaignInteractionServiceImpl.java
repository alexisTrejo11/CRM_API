package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CRM.Repository.CustomerRepository;
import at.backend.CRM.CommonClasses.Exceptions.InvalidInputException;
import at.backend.CRM.MarketingProject.AutoMappers.CampaignInteractionMappers;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignInteractionInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignInteraction;
import at.backend.CRM.MarketingProject.Models.MarketingCampaign;
import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.MarketingProject.Repository.CampaignInteractionRepository;
import at.backend.CRM.MarketingProject.Repository.MarketingCampaignRepository;
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
        CampaignInteraction interaction = campaignInteractionMappers.inputToEntity(insertDTO);

        validate(insertDTO);

        fetchInteractionRelationships(interaction, insertDTO.getCampaignId(), insertDTO.getCustomerId());

        campaignInteractionRepository.save(interaction);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    @Override
    public CampaignInteractionDTO update(Long id, CampaignInteractionInsertDTO insertDTO) {
        CampaignInteraction existingInteraction = getInteraction(id);

        campaignInteractionMappers.updateEntity(existingInteraction, insertDTO);

        campaignInteractionRepository.save(existingInteraction);

        return campaignInteractionMappers.entityToDTO(existingInteraction);
    }

    @Override
    public void delete(Long id) {
        CampaignInteraction interaction = getInteraction(id);

        campaignInteractionRepository.delete(interaction);
    }

    @Override
    public Page<CampaignInteractionDTO> getAll(Pageable pageable) {
        return campaignInteractionRepository.findAll(pageable).map(campaignInteractionMappers::entityToDTO);
    }

    @Override
    public CampaignInteractionDTO getById(Long id) {
        CampaignInteraction interaction = getInteraction(id);

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
        CampaignInteraction interaction = getInteraction(id);

        interaction.getProperties().putAll(properties);

        campaignInteractionRepository.save(interaction);

        return campaignInteractionMappers.entityToDTO(interaction);
    }

    public Double calculateTotalConversionValue(Long campaignId) {
        List<CampaignInteraction> interactions = campaignInteractionRepository.findByCampaignId(campaignId);

        return interactions.stream()
                .map(CampaignInteraction::getConversionValue)
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
    }

    @Override
    public void validate(CampaignInteractionInsertDTO insertDTO) {
        if (insertDTO.getMarketingInteractionType() == null) {
            throw new InvalidInputException("Interaction type cannot be null");
        }
        if (insertDTO.getInteractionDate() == null) {
            throw new InvalidInputException("Interaction date cannot be null");
        }
        if (insertDTO.getConversionValue() != null && insertDTO.getConversionValue() < 0) {
            throw new InvalidInputException("Conversion value must be greater than or equal to zero");
        }
    }

    private CampaignInteraction getInteraction(Long id) {
        return campaignInteractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interaction not found with ID: " + id));
    }

    private Customer getCustomerById(Long customerId) {
         return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
    }

    private MarketingCampaign getCampaign(Long campaignID) {
        return marketingCampaignRepository.findById(campaignID)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with ID: " + campaignID));
    }

    private void fetchInteractionRelationships(CampaignInteraction interaction, Long campaignId, Long customerId) {
        Customer customer = getCustomerById(customerId);
        MarketingCampaign campaign = getCampaign(campaignId);
        interaction.setCampaign(campaign);
        interaction.setCustomer(customer);
    }
}