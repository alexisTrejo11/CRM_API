package at.backend.MarketingCompany.marketing.customer;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class CustomerSegmentDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 2000, message = "Segment criteria cannot exceed 2000 characters")
    private String segmentCriteria;

    private boolean dynamic;

    private Map<String, String> rules;

    private List<Long> campaignIds;

    private List<Long> customerIds;

    private LocalDateTime lastUpdated;
}
