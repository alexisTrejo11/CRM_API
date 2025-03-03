package at.backend.MarketingCompany.customer.infrastructure;


import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CustomerInput(
        @NotBlank(message = "First name cannot be blank. Please provide a valid first name.")
        @Size(max = 50, message = "First name cannot exceed 50 characters.")
        String firstName,

        @NotBlank(message = "Last name cannot be blank. Please provide a valid last name.")
        @Size(max = 50, message = "Last name cannot exceed 50 characters.")
        String lastName,

        @Email(message = "Email must be a valid email address.")
        @Size(max = 100, message = "Email cannot exceed 100 characters.")
        String email,

        @Pattern(regexp = "\\+?\\d{7,15}", message = "Phone number is invalid. Must contain only digits and optional '+'.")
        @Size(max = 20, message = "Phone cannot exceed 20 characters.")
        String phone,

        @Size(max = 100, message = "Company name cannot exceed 100 characters.")
        String company
) {}
