package at.backend.CRM.CRM.Repository;

import at.backend.CRM.CRM.Models.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

}