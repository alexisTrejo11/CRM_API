package at.backend.MarketingProject.Service;

import at.backend.MarketingProject.AutoMappers.CustomerSegmentMappers;
import at.backend.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.MarketingProject.Models.CustomerSegment;
import at.backend.CRM.Models.Customer;
import at.backend.MarketingProject.Repository.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerSegmentService {

    private final CustomerSegmentRepository customerSegmentRepository;
    private final CustomerSegmentMappers customerSegmentMappers;

    public CustomerSegmentDTO createSegment(CustomerSegmentInsertDTO input) {
        CustomerSegment segment = customerSegmentMappers.inputToEntity(input);

        validateSegment(segment);
        customerSegmentRepository.save(segment);

        return customerSegmentMappers.entityToDTO(segment);
    }

    public CustomerSegmentDTO updateSegment(Long id, CustomerSegmentInsertDTO input) {
        CustomerSegment existingSegment = getSegment(id);

        customerSegmentMappers.updateEntity(existingSegment, input);

        customerSegmentRepository.save(existingSegment);

        return customerSegmentMappers.entityToDTO(existingSegment);
    }

    public void deleteSegment(Long id) {
        CustomerSegment segment = getSegment(id);

        customerSegmentRepository.delete(segment);
    }

    public CustomerSegmentDTO getSegmentById(Long id) {
        CustomerSegment segment = getSegment(id);
        return customerSegmentMappers.entityToDTO(segment);
    }

    public CustomerSegment getSegment(Long id) {
        return customerSegmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer segment not found with ID: " + id));
    }

    public List<CustomerSegment> getAllSegments() {
        return customerSegmentRepository.findAll();
    }

    public List<CustomerSegment> getSegmentsByDynamic(boolean dynamic) {
        return customerSegmentRepository.findByDynamic(dynamic);
    }

    public CustomerSegment updateSegmentRules(Long id, Map<String, String> rules) {
        CustomerSegment segment = getSegment(id);

        segment.getRules().putAll(rules);
        return customerSegmentRepository.save(segment);
    }

    public CustomerSegment addCustomersToSegment(Long id, List<Customer> customers) {
        CustomerSegment segment = getSegment(id);

        segment.getCustomers().addAll(customers);
        return customerSegmentRepository.save(segment);
    }

    public CustomerSegment removeCustomersFromSegment(Long id, List<Customer> customers) {
        CustomerSegment segment = getSegment(id);

        segment.getCustomers().removeAll(customers);
        return customerSegmentRepository.save(segment);
    }

    private void validateSegment(CustomerSegment segment) {
        if (segment.getName() == null || segment.getName().isEmpty()) {
            throw new IllegalArgumentException("Segment name cannot be empty");
        }
        if (segment.getSegmentCriteria() == null || segment.getSegmentCriteria().isEmpty()) {
            throw new IllegalArgumentException("Segment criteria cannot be empty");
        }
    }
}