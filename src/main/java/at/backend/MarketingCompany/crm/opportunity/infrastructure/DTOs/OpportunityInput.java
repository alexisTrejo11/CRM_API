package at.backend.MarketingCompany.crm.opportunity.infrastructure.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record OpportunityInput(

        @NotBlank(message = "Title cannot be blank.")
        @Size(max = 255, message = "Title cannot exceed 255 characters.")
        String title,

        @NotNull(message = "Amount cannot be null.")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
        BigDecimal amount,

        @NotBlank(message = "Stage cannot be blank.")
        @Pattern(regexp = "LEAD|QUALIFIED|PROPOSAL|NEGOTIATION|CLOSED_WON|CLOSED_LOST", message = "Invalid stage. Must be one of: LEAD, QUALIFIED, PROPOSAL, NEGOTIATION, CLOSED_WON, CLOSED_LOST.")
        String stage,

        @NotNull(message = "Expected close date cannot be null.")
        @FutureOrPresent(message = "Expected close date must be today or in the future.")
        LocalDate expectedCloseDate,

        @Positive
        Long customerId
) {}
