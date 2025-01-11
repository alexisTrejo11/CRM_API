package at.backend.CRM.Repository;

import at.backend.CRM.Models.QuoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteItemRepository extends JpaRepository<QuoteItem, Long> {

}