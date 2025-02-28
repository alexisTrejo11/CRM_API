package at.backend.CRM.CRM.Inputs;

import at.backend.CRM.CRM.Utils.enums.QuoteStatus;
import jakarta.validation.constraints.*;
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

        @NotNull(message = "Status is required.")
        QuoteStatus status,

        @NotNull(message = "Items list is required.")
        @Size(min = 1, message = "At least one item must be provided.")
        List<@NotNull(message = "Item cannot be null.") QuoteItemInput> items
) {}
