package at.backend.CRM.Repository;

import at.backend.CRM.Models.Customer;
import at.backend.CRM.Models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

}