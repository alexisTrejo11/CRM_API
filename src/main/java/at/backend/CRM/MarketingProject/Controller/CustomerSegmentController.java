package at.backend.CRM.MarketingProject.Controller;

import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.CRM.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.CRM.MarketingProject.Service.CustomerSegmentService;
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
        return campaignSegmentService.getSegmentById(id);
    }

    @MutationMapping
    public CustomerSegmentDTO createSegment(@Valid @Argument CustomerSegmentInsertDTO input) {
        return campaignSegmentService.createSegment(input);
    }

    @MutationMapping
    public CustomerSegmentDTO updateSegment(@Valid @Argument CustomerSegmentInsertDTO input,
                                               @Argument Long id) {
        return campaignSegmentService.updateSegment(id, input);
    }


    @MutationMapping
    public boolean deleteSegment(@Argument Long id) {
        campaignSegmentService.deleteSegment(id);
        
        return true;
    }
}
