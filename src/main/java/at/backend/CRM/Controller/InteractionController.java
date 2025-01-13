package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.InteractionInput;
import at.backend.CRM.Inputs.PageInput;
import at.backend.CRM.Models.Interaction;
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
public class InteractionController {
    private final CommonService<Interaction, InteractionInput> service;

    @QueryMapping
    public Page<Interaction> getAllInteractions(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.findAll(pageable);
    }

    @QueryMapping
    public Interaction getInteractionById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Interaction());
    }

    @MutationMapping
    public Interaction createInteraction(@Valid @Argument InteractionInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Interaction updateInteraction(@Valid @Argument Long id, @Argument InteractionInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteInteraction(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
