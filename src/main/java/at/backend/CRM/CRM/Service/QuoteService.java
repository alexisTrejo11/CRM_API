package at.backend.CRM.CRM.Service;

import at.backend.CRM.CRM.Inputs.QuoteInput;
import at.backend.CRM.CRM.Inputs.QuoteItemInput;
import at.backend.CRM.CRM.Models.Quote;
import at.backend.CRM.CommonClasses.Service.CommonService;

public interface QuoteService extends CommonService<Quote, QuoteInput> {
    Quote addItem(Long id, QuoteItemInput input);
    Quote deleteItem(Long itemId);
}
