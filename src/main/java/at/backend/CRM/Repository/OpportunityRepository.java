package at.backend.CRM.Repository;

import at.backend.CRM.Models.Interaction;
import at.backend.CRM.Models.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

}