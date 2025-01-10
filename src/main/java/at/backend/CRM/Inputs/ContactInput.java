package at.backend.CRM.Inputs;

import jakarta.validation.constraints.*;

public record ContactInput(
        @NotBlank(message = "First name cannot be blank. Please provide a valid first name.")
        @Size(max = 50, message = "First name cannot exceed 50 characters.")
        String firstName,

        @NotBlank(message = "Last name cannot be blank. Please provide a valid last name.")
        @Size(max = 50, message = "Last name cannot exceed 50 characters.")
        String lastName,

        @NotBlank(message = "Email cannot be blank. Please provide a valid email address.")
        @Email(message = "Email must be in a valid format, e.g., user@example.com.")
        String email,

        @Pattern(regexp = "^\\+?[0-9. ()-]{7,15}$", message = "Phone number must be valid and between 7 and 15 characters.")
        String phone,

        @Size(max = 50, message = "Position cannot exceed 50 characters.")
        String position,

        @NotNull(message = "Company ID cannot be null. Please provide a valid company ID.")
        Long companyId
) {}
