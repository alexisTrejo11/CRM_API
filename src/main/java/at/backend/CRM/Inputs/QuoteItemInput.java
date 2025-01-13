package at.backend.CRM.Inputs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record QuoteItemInput(

        @NotNull(message = "Quote ID is required.")
        Long quoteId,

        @NotNull(message = "Service Package ID is required.")
        Long servicePackageId
)
{}
