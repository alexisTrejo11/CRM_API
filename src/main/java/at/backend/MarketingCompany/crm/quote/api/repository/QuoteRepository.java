package at.backend.MarketingCompany.crm.quote.api.repository;

import at.backend.MarketingCompany.crm.quote.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

}