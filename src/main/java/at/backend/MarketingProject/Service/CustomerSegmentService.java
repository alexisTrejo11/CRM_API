package at.backend.MarketingProject.Service;

import at.backend.MarketingProject.Models.CustomerSegment;
import at.backend.CRM.Models.Customer;
import at.backend.MarketingProject.Repository.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerSegmentService {

    private final CustomerSegmentRepository customerSegmentRepository;

    public CustomerSegment createSegment(CustomerSegment segment) {
        validateSegment(segment);
        return customerSegmentRepository.save(segment);
    }

    public CustomerSegment updateSegment(Long id, CustomerSegment updatedSegment) {
        Optional<CustomerSegment> existingSegment = customerSegmentRepository.findById(id);
        if (existingSegment.isPresent()) {
            CustomerSegment segment = existingSegment.get();
            segment.setName(updatedSegment.getName());
            segment.setDescription(updatedSegment.getDescription());
            segment.setSegmentCriteria(updatedSegment.getSegmentCriteria());
            segment.setDynamic(updatedSegment.isDynamic());
            segment.setRules(updatedSegment.getRules());
            return customerSegmentRepository.save(segment);
        } else {
            throw new RuntimeException("Customer segment not found with ID: " + id);
        }
    }

    public void deleteSegment(Long id) {
        Optional<CustomerSegment> segment = customerSegmentRepository.findById(id);
        if (segment.isPresent()) {
            customerSegmentRepository.delete(segment.get());
        } else {
            throw new RuntimeException("Customer segment not found with ID: " + id);
        }
    }

    public CustomerSegment getSegmentById(Long id) {
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
        CustomerSegment segment = getSegmentById(id);
        segment.getRules().putAll(rules);
        return customerSegmentRepository.save(segment);
    }

    public CustomerSegment addCustomersToSegment(Long id, List<Customer> customers) {
        CustomerSegment segment = getSegmentById(id);
        segment.getCustomers().addAll(customers);
        return customerSegmentRepository.save(segment);
    }

    public CustomerSegment removeCustomersFromSegment(Long id, List<Customer> customers) {
        CustomerSegment segment = getSegmentById(id);
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