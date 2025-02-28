package at.backend.MarketingProject.Controller;

import at.backend.MarketingProject.DTOs.CustomerSegmentDTO;
import at.backend.MarketingProject.DTOs.CustomerSegmentInsertDTO;
import at.backend.MarketingProject.Service.CustomerSegmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing-campaigns/customer-segments")
@RequiredArgsConstructor
public class CustomerSegmentController {
    private final CustomerSegmentService campaignSegmentService;

    @GetMapping("/{id}")
    public CustomerSegmentDTO getSegmentByID(@PathVariable Long id) {
        return campaignSegmentService.getSegmentById(id);
    }

    @PostMapping
    public CustomerSegmentDTO CreateSegment(@Valid @RequestBody CustomerSegmentInsertDTO insertDTO) {
        return campaignSegmentService.createSegment(insertDTO);
    }

    @PutMapping("/{id}")
    public CustomerSegmentDTO updateSegment(@Valid @RequestBody CustomerSegmentInsertDTO insertDTO,
                                               @PathVariable Long id) {
        return campaignSegmentService.updateSegment(id, insertDTO);
    }


    @DeleteMapping("/{id}")
    public boolean deleteSegmentByID(@PathVariable Long id) {
        campaignSegmentService.deleteSegment(id);
        
        return true;
    }
}
