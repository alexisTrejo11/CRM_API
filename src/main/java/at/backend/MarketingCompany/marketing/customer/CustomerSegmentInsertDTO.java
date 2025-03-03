package at.backend.MarketingCompany.marketing.customer;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSegmentInsertDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 2000, message = "Segment criteria cannot exceed 2000 characters")
    private String segmentCriteria;

    private boolean dynamic;

    private Map<String, String> rules;
}