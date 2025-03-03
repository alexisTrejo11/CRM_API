package at.backend.MarketingCompany.crm.opportunity.api.repository;

import at.backend.MarketingCompany.crm.opportunity.domain.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

}