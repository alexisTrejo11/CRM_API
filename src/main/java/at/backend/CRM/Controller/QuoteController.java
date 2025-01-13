package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.QuoteInput;
import at.backend.CRM.Inputs.PageInput;
import at.backend.CRM.Models.Quote;
import at.backend.CRM.Service.CommonService;
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
    private final CommonService<Quote, QuoteInput> service;

    @QueryMapping
    public Page<Quote> getAllQuotes(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.findAll(pageable);
    }

    @QueryMapping
    public Quote getQuoteById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Quote());
    }

    @MutationMapping
    public Quote createQuote(@Valid @Argument QuoteInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Quote updateQuote(@Valid @Argument Long id, @Argument QuoteInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteQuote(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
