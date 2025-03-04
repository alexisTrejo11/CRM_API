package at.backend.MarketingCompany.crm.quote.api.service;

import at.backend.MarketingCompany.crm.quote.domain.Quote;
import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.crm.quote.infrastructure.DTOs.QuoteInput;
import at.backend.MarketingCompany.crm.quote.infrastructure.DTOs.QuoteItemInput;

public interface QuoteService extends CommonService<Quote, QuoteInput, Long> {
    Quote addItem(Long id, QuoteItemInput input);
    Quote deleteItem(Long itemId);
}
