package at.backend.CRM.Inputs;

import at.backend.CRM.Utils.enums.FeedbackType;
import at.backend.CRM.Utils.enums.InteractionType;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record InteractionInput(
        @NotNull(message = "Customer ID cannot be null")
        Long customerId,

        @NotNull(message = "Type cannot be null")
        InteractionType type,

        @NotNull(message = "Date and time cannot be null")
        @FutureOrPresent(message = "Date and time must be in the present or future")
        LocalDateTime dateTime,

        @Size(max = 5000, message = "Description cannot exceed 5000 characters")
        String description,

        @NotBlank(message = "Outcome cannot be blank")
        @Size(max = 255, message = "Outcome cannot exceed 255 characters")
        String outcome,

        @NotNull(message = "Feedback Type cannot be null")
        FeedbackType feedbackType,

        @Size(max = 255, message = "Channel preference cannot exceed 255 characters")
        String channelPreference
) {}

