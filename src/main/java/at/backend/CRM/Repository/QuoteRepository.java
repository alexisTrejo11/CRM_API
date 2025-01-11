package at.backend.CRM.Repository;

import at.backend.CRM.Models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

}