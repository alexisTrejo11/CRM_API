package at.backend.CRM.CRM.Inputs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record QuoteItemInput(
        @NotNull(message = "Service Package ID is required.")
        Long servicePackageId,

        @NotNull(message = "Discount Percentage is required.")
        @Positive(message = "Discount Percentage  must be positive.")
        BigDecimal discountPercentage
)
{}
