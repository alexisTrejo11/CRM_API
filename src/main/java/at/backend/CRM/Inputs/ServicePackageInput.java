package at.backend.CRM.Inputs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ServicePackageInput(
        @NotBlank(message = "ServicePackage name cannot be blank. Please provide a valid name.")
        @Size(max = 100, message = "ServicePackage name cannot exceed 100 characters.")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters.")
        String description,

        @NotNull(message = "Price cannot be null.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
        @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal number with up to 10 integer digits and 2 decimal places.")
        BigDecimal price,

        @NotBlank(message = "Category cannot be blank. Please provide a valid category.")
        @Size(max = 50, message = "Category cannot exceed 50 characters.")
        String category,

        @NotNull(message = "isActive cannot be null. Please specify if the product is active.")
        Boolean isActive
) {}
