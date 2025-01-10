package at.backend.CRM.Inputs;

import at.backend.CRM.Models.enums.DealStage;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record DealInput(
        @NotBlank(message = "Deal name cannot be blank. Please provide a valid name.")
        @Size(max = 100, message = "Deal name cannot exceed 100 characters.")
        String name,

        @NotNull(message = "Deal value cannot be null. Please provide a valid monetary value.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Deal value must be greater than 0.")
        @Digits(integer = 10, fraction = 2, message = "Deal value must be a valid decimal number with up to 2 decimal places.")
        BigDecimal value,

        @NotNull(message = "Deal stage cannot be null. Please select a valid stage.")
        DealStage stage,

        @Future(message = "Expected close date must be in the future.")
        @NotNull(message = "Expected close date cannot be null. Please provide a valid date.")
        LocalDateTime expectedCloseDate,

        @NotNull(message = "Company ID cannot be null. Please provide a valid company ID.")
        Long companyId,

        @NotEmpty(message = "Contact IDs cannot be empty. Please associate at least one contact.")
        Set<Long> contactIds
) {}
