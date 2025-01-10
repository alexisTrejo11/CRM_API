package at.backend.CRM.Inputs;

import at.backend.CRM.Models.enums.ActivityStatus;
import at.backend.CRM.Models.enums.ActivityType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ActivityInput(
        @NotNull(message = "Activity type cannot be null. Please provide a valid type.")
        ActivityType type,

        @NotBlank(message = "Description cannot be blank. Please provide a meaningful description.")
        @Size(max = 255, message = "Description cannot exceed 255 characters.")
        String description,

        @FutureOrPresent(message = "Due date cannot be in the past. Please provide a valid date.")
        LocalDateTime dueDate,

        @NotNull(message = "Contact ID cannot be null. Please provide a valid contact ID.")
        @Positive(message = "Contact ID must be a positive number.")
        Long contactId,

        @NotNull(message = "Deal ID cannot be null. Please provide a valid deal ID.")
        @Positive(message = "Deal ID must be a positive number.")
        Long dealId,

        @NotNull(message = "Assigned User ID cannot be null. Please provide a valid user ID.")
        @Positive(message = "Assigned User ID must be a positive number.")
        Long assignedUserId,

        @NotNull(message = "Activity status cannot be null. Please provide a valid status.")
        ActivityStatus status
) {}
