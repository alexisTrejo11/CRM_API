package at.backend.CRM.Inputs;

import at.backend.CRM.Models.enums.UserRole;
import jakarta.validation.constraints.*;

public record UserInput(
        @NotBlank(message = "First name cannot be blank. Please provide a valid first name.")
        @Size(max = 50, message = "First name cannot exceed 50 characters.")
        String firstName,

        @NotBlank(message = "Last name cannot be blank. Please provide a valid last name.")
        @Size(max = 50, message = "Last name cannot exceed 50 characters.")
        String lastName,

        @NotBlank(message = "Email cannot be blank. Please provide a valid email address.")
        @Email(message = "Email must be a valid email address.")
        @Size(max = 100, message = "Email cannot exceed 100 characters.")
        String email,

        @NotBlank(message = "Password cannot be blank. Please provide a valid password.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        String password,

        @NotNull(message = "User role cannot be null. Please select a valid role.")
        UserRole role
) {}
