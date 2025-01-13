package at.backend.CRM.Repository;

import at.backend.CRM.Models.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

}