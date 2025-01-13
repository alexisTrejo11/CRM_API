package at.backend.CRM.Inputs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record QuoteInput(

        @NotNull(message = "Customer ID is required.")
        Long customerId,

        @NotNull(message = "Opportunity ID is required.")
        Long opportunityId,

        @NotNull(message = "Valid until date is required.")
        @Future(message = "Valid until date must be in the future.")
        LocalDate validUntil,

        @NotNull(message = "Total amount is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero.")
        @Digits(integer = 10, fraction = 2, message = "Total amount must be a valid decimal with up to 2 decimal places.")
        BigDecimal totalAmount,

        @NotBlank(message = "Status is required.")
        @Pattern(regexp = "^(DRAFT|SENT|ACCEPTED|REJECTED)$", message = "Status must be one of the following: DRAFT, SENT, ACCEPTED, REJECTED.")
        String status,

        @NotNull(message = "Items list is required.")
        @Size(min = 1, message = "At least one item must be provided.")
        List<@NotNull(message = "Item cannot be null.") QuoteItemInput> items
) {}
