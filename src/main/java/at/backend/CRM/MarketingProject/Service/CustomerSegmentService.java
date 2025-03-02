package at.backend.CRM.MarketingProject.Service;

import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.CommonClasses.Service.CommonService;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricDTO;
import at.backend.CRM.MarketingProject.DTOs.CampaignMetricInsertDTO;
import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.CRM.MarketingProject.Models.CampaignMetric;
import at.backend.CRM.MarketingProject.Models.CustomerSegment;

import java.util.List;
import java.util.Map;

public interface CustomerSegmentService extends CommonService<CustomerSegmentDTO, CustomerSegmentInsertDTO> {
    CustomerSegmentDTO updateSegmentRules(Long id, Map<String, String> rules);
    List<CustomerSegmentDTO> getSegmentsByDynamic(boolean dynamic);
    CustomerSegmentDTO addCustomersToSegment(Long id, List<Customer> customers);
    CustomerSegmentDTO removeCustomersFromSegment(Long id, List<Customer> customers);
}
