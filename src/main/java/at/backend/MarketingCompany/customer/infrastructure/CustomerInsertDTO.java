package at.backend.MarketingCompany.customer.infrastructure;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class CustomerInsertDTO {

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name cannot exceed 50 characters")
        private String firstName;

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name cannot exceed 50 characters")
        private String lastName;

        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        private String email;

        @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
        @Size(max = 20, message = "Phone number cannot exceed 20 characters")
        private String phone;

        @NotBlank(message = "Company name is required")
        @Size(max = 100, message = "Company name cannot exceed 100 characters")
        private String company;

        @NotBlank(message = "Industry is required")
        @Size(max = 50, message = "Industry cannot exceed 50 characters")
        private String industry;

        @Size(max = 100, message = "Brand voice cannot exceed 100 characters")
        private String brandVoice;

        @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})(,#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}))*$",
                message = "Invalid brand colors format (hexadecimal values separated by commas)")
        private String brandColors;

        @Size(max = 50, message = "Target market cannot exceed 50 characters")
        private String targetMarket;

        private Set<String> competitorUrls;

        @Pattern(regexp = "^@[\\w.-]+(,@[\\w.-]+)*$",
                message = "Invalid social media handles format (handles must start with '@' and be comma-separated)")
        private String socialMediaHandles;

        public boolean hasContactDetails() {
                return (email != null && !email.isBlank()) || (phone != null && !phone.isBlank());
        }

        public boolean hasCompetitors() {
                return competitorUrls != null && !competitorUrls.isEmpty();
        }
}