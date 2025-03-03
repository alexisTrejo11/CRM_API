package at.backend.MarketingCompany.crm.quote.api.controller;

import at.backend.MarketingCompany.common.utils.PageInput;
import at.backend.MarketingCompany.crm.quote.domain.Quote;
import at.backend.MarketingCompany.crm.quote.api.service.QuoteService;
import at.backend.MarketingCompany.crm.quote.infrastructure.DTOs.QuoteInput;
import at.backend.MarketingCompany.crm.quote.infrastructure.DTOs.QuoteItemInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService service;

    @QueryMapping
    public Page<Quote> getAllQuotes(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.getAll(pageable);
    }

    @QueryMapping
    public Quote getQuoteById(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public Quote createQuote(@Valid @Argument QuoteInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Quote addQuoteItem(@Valid @Argument Long id, @Argument QuoteItemInput input) {
        return service.addItem(id, input);
    }

    @MutationMapping
    public Quote deleteQuoteItem(@Valid @Argument Long itemId) {
        return service.deleteItem(itemId);
    }


    @MutationMapping
    public boolean deleteQuote(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
