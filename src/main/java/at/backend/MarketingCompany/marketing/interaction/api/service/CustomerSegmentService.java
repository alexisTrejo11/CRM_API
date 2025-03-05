package at.backend.MarketingCompany.marketing.interaction.api.service;

import at.backend.MarketingCompany.customer.api.repository.CustomerModel;
import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.marketing.customer.CustomerSegmentDTO;
import at.backend.MarketingCompany.marketing.customer.CustomerSegmentInsertDTO;

import java.util.List;
import java.util.Map;

public interface CustomerSegmentService extends CommonService<CustomerSegmentDTO, CustomerSegmentInsertDTO, Long> {
    CustomerSegmentDTO updateSegmentRules(Long id, Map<String, String> rules);
    List<CustomerSegmentDTO> getSegmentsByDynamic(boolean dynamic);
    CustomerSegmentDTO addCustomersToSegment(Long id, List<CustomerModel> customerModels);
    CustomerSegmentDTO removeCustomersFromSegment(Long id, List<CustomerModel> customerModels);
}
