package at.backend.CRM.Inputs;

import at.backend.CRM.Utils.enums.TaskPriority;
import at.backend.CRM.Utils.enums.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskInput(
        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Opportunity ID is required")
        Long opportunityId,

        Long assignedToUserId,

        @NotEmpty(message = "Title cannot be empty")
        @Size(max = 255, message = "Title must be less than 255 characters")
        String title,

        @Size(max = 500, message = "Description cannot be longer than 500 characters")
        String description,

        @NotNull(message = "Due Date is required")
        LocalDateTime dueDate,

        @NotNull(message = "Status is required") TaskStatus status,
        @NotNull(message = "Priority is required") TaskPriority priority
) {}