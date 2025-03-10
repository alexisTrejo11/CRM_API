package at.backend.MarketingCompany.marketing.customer;

import at.backend.MarketingCompany.marketing.interaction.api.service.CustomerSegmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CustomerSegmentController {

    private final CustomerSegmentService campaignSegmentService;

    @QueryMapping
    public CustomerSegmentDTO getSegmentById(@Argument Long id) {
        return campaignSegmentService.getById(id);
    }

    @MutationMapping
    public CustomerSegmentDTO createSegment(@Valid @Argument CustomerSegmentInsertDTO input) {
        return campaignSegmentService.create(input);
    }

    @MutationMapping
    public CustomerSegmentDTO updateSegment(@Valid @Argument CustomerSegmentInsertDTO input,
                                            @Argument Long id) {
        return campaignSegmentService.update(id, input);
    }

    @MutationMapping
    public boolean deleteSegment(@Argument Long id) {
        campaignSegmentService.delete(id);
        
        return true;
    }
}
