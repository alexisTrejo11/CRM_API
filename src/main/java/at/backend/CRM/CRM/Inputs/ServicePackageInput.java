package at.backend.CRM.CRM.Inputs;

import at.backend.CRM.CRM.Utils.enums.Complexity;
import at.backend.CRM.CRM.Utils.enums.Frequency;
import at.backend.CRM.CRM.Utils.enums.ServiceType;
import at.backend.CRM.CRM.Utils.enums.SocialNetworkPlatform;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ServicePackageInput(

        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must not exceed 100 characters.")
        String name,

        @Size(max = 5000, message = "Description must not exceed 5000 characters.")
        String description,

        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
        @Digits(integer = 8, fraction = 2, message = "Price must be a valid decimal with up to 2 decimal places.")
        BigDecimal price,

        @NotNull(message = "Service type is required.")
        ServiceType serviceType,

        @Size(max = 5000, message = "Deliverables must not exceed 5000 characters.")
        String deliverables,

        @NotNull(message = "Estimated hours are required.")
        @Min(value = 1, message = "Estimated hours must be at least 1.")
        Integer estimatedHours,

        @NotNull(message = "Complexity is required.")
        Complexity complexity,

        @NotNull(message = "Recurring status is required.")
        Boolean isRecurring,

        Frequency frequency,

        @Min(value = 1, message = "Project duration must be at least 1 month.")
        Integer projectDuration,

        @NotNull(message = "KPI list is required.")
        @Size(min = 1, message = "At least one KPI must be provided.")
        List<@NotBlank(message = "KPI cannot be blank.") String> kpis,

        @NotNull(message = "Social network platforms list is required.")
        @Size(min = 1, message = "At least one platform must be provided.")
        List<SocialNetworkPlatform> socialNetworkPlatforms,

        @NotNull(message = "Active status is required.")
        Boolean active
) {}
