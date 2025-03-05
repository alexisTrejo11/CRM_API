package at.backend.MarketingCompany.customer.api.service;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.customer.domain.Customer;
import at.backend.MarketingCompany.customer.infrastructure.CustomerDTO;
import at.backend.MarketingCompany.customer.infrastructure.CustomerInsertDTO;

import java.util.UUID;

public interface CustomerService extends CommonService<CustomerDTO, CustomerInsertDTO, UUID> {
    boolean isBlocked(UUID id);
    boolean hasSocialMediaHandles(UUID id);
    boolean hasCompetitors(UUID id);
}
