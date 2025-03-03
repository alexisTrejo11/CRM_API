package at.backend.MarketingCompany.marketing.interaction.api.service;

import at.backend.MarketingCompany.common.exceptions.InvalidInputException;
import at.backend.MarketingCompany.marketing.customer.CustomerSegmentMappers;
import at.backend.MarketingCompany.marketing.customer.CustomerSegmentDTO;
import at.backend.MarketingCompany.marketing.customer.CustomerSegmentInsertDTO;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CustomerSegment;
import at.backend.MarketingCompany.customer.domain.Customer;
import at.backend.MarketingCompany.marketing.interaction.api.repository.CustomerSegmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerSegmentServiceImpl implements CustomerSegmentService {

    private final CustomerSegmentRepository customerSegmentRepository;
    private final CustomerSegmentMappers customerSegmentMappers;

    @Override
    public CustomerSegmentDTO create(CustomerSegmentInsertDTO input) {
        CustomerSegment segment = customerSegmentMappers.inputToEntity(input);

        validate(input);
        customerSegmentRepository.save(segment);

        return customerSegmentMappers.entityToDTO(segment);
    }

    @Override
    public CustomerSegmentDTO update(Long id, CustomerSegmentInsertDTO input) {
        CustomerSegment existingSegment = getSegment(id);

        customerSegmentMappers.updateEntity(existingSegment, input);

        customerSegmentRepository.save(existingSegment);

        return customerSegmentMappers.entityToDTO(existingSegment);
    }

    @Override
    public void delete(Long id) {
        CustomerSegment segment = getSegment(id);

        customerSegmentRepository.delete(segment);
    }

    @Override
    public CustomerSegmentDTO getById(Long id) {
        CustomerSegment segment = getSegment(id);
        return customerSegmentMappers.entityToDTO(segment);
    }

    @Override
    public Page<CustomerSegmentDTO> getAll(Pageable pageable) {
        return customerSegmentRepository.findAll(pageable).map(customerSegmentMappers::entityToDTO);
    }

    @Override
    public List<CustomerSegmentDTO> getSegmentsByDynamic(boolean dynamic) {
        return customerSegmentRepository.findByDynamic(dynamic)
                .stream()
                .map(customerSegmentMappers::entityToDTO)
                .toList();
    }

    @Override
    public CustomerSegmentDTO updateSegmentRules(Long id, Map<String, String> rules) {
        CustomerSegment segment = getSegment(id);

        segment.getRules().putAll(rules);
        customerSegmentRepository.save(segment);

        return customerSegmentMappers.entityToDTO(segment);
    }

    @Override
    public CustomerSegmentDTO addCustomersToSegment(Long id, List<Customer> customers) {
        CustomerSegment segment = getSegment(id);

        segment.getCustomers().addAll(customers);
        customerSegmentRepository.save(segment);

        return customerSegmentMappers.entityToDTO(segment);
    }

    @Override
    public CustomerSegmentDTO removeCustomersFromSegment(Long id, List<Customer> customers) {
        CustomerSegment segment = getSegment(id);

        segment.getCustomers().removeAll(customers);
        customerSegmentRepository.save(segment);

        return customerSegmentMappers.entityToDTO(segment);
    }

    @Override
    public void validate(CustomerSegmentInsertDTO insertDTO) {
        if (insertDTO.getName() == null || insertDTO.getName().isEmpty()) {
            throw new InvalidInputException("Segment name cannot be empty");
        }
        if (insertDTO.getSegmentCriteria() == null || insertDTO.getSegmentCriteria().isEmpty()) {
            throw new InvalidInputException("Segment criteria cannot be empty");
        }
    }

    private CustomerSegment getSegment(Long id) {
        return customerSegmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer segment not found with ID: " + id));
    }
}