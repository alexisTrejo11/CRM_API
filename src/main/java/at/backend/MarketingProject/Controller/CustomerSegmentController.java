package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.MarketingProject.Service.CustomerSegmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marketing-campaigns/customer-segments")
@RequiredArgsConstructor
public class CustomerSegmentController {
    private final CustomerSegmentService campaignSegmentService;

    @QueryMapping("/{id}")
    public CustomerSegmentDTO getSegmentById(@PathVariable Long id) {
        return campaignSegmentService.getSegmentById(id);
    }

    @MutationMapping
    public CustomerSegmentDTO CreateSegment(@Valid @RequestBody CustomerSegmentInsertDTO insertDTO) {
        return campaignSegmentService.createSegment(insertDTO);
    }

    @MutationMapping("/{id}")
    public CustomerSegmentDTO updateSegment(@Valid @RequestBody CustomerSegmentInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignSegmentService.updateSegment(id, insertDTO);
    }


    @MutationMapping("/{id}")
    public boolean deleteSegment(@PathVariable Long id) {
        campaignSegmentService.deleteSegment(id);
        
        return true;
    }
}
