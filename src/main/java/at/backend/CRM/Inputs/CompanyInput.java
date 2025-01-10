package at.backend.CRM.Inputs;

import jakarta.validation.constraints.*;

public record CompanyInput(
        @NotBlank(message = "Company name cannot be blank. Please provide a valid name.")
        @Size(max = 100, message = "Company name cannot exceed 100 characters.")
        String name,

        @NotBlank(message = "Tax number cannot be blank. Please provide a valid tax number.")
        @Pattern(regexp = "\\d{8,15}", message = "Tax number must be between 8 and 15 digits.")
        String taxNumber,

        @NotBlank(message = "Industry cannot be blank. Please provide a valid industry.")
        @Size(max = 50, message = "Industry cannot exceed 50 characters.")
        String industry,

        @NotBlank(message = "Website cannot be blank. Please provide a valid URL.")
        @Pattern(regexp = "^(https?://)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$",
                message = "Website must be a valid URL, e.g., https://example.com.")
        String website
) {}
