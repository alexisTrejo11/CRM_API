package at.backend.CRM.CRM.Repository;

import at.backend.CRM.CRM.Models.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

}