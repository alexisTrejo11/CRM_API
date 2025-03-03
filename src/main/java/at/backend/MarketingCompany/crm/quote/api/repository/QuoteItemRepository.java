package at.backend.MarketingCompany.crm.quote.api.repository;

import at.backend.MarketingCompany.crm.quote.domain.QuoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteItemRepository extends JpaRepository<QuoteItem, Long> {

}