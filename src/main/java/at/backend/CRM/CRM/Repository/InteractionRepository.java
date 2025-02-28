package at.backend.CRM.CRM.Repository;

import at.backend.CRM.CRM.Models.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

}