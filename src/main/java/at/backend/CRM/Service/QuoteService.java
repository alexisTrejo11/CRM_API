package at.backend.CRM.Service;

import at.backend.CRM.Inputs.QuoteInput;
import at.backend.CRM.Inputs.QuoteItemInput;
import at.backend.CRM.Models.Quote;

public interface QuoteService extends  CommonService<Quote, QuoteInput> {
    Quote addItem(Long id, QuoteItemInput input);
    Quote deleteItem(Long itemId);
}
