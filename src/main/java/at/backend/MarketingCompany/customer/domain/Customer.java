package at.backend.MarketingCompany.customer.domain;

import at.backend.MarketingCompany.customer.domain.ValueObjects.BusinessProfile;
import at.backend.MarketingCompany.customer.domain.ValueObjects.ContactDetails;
import at.backend.MarketingCompany.customer.domain.ValueObjects.CustomerId;
import at.backend.MarketingCompany.customer.domain.ValueObjects.PersonalInfo;
import at.backend.MarketingCompany.customer.domain.excpetions.CustomerDomainException;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class Customer {
    private final CustomerId id;
    private PersonalInfo personalInfo;
    private ContactDetails contactDetails;
    private BusinessProfile businessProfile;
    private final Set<Long> opportunities = new HashSet<>();
    private final Set<Long> interactions = new HashSet<>();
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isBlocked() {
        return false; // Placeholder
    }

    public void updateContactDetails(ContactDetails newDetails) {
        validateContactDetails(newDetails);
        this.contactDetails = newDetails;
        this.updatedAt = LocalDateTime.now();
    }

    public void validate() {
        if (personalInfo == null || personalInfo.getFirstName() == null || personalInfo.getFirstName().isBlank()) {
            throw new CustomerDomainException("First name is required");
        }
        if (personalInfo.getLastName().isBlank()) {
            throw new CustomerDomainException("Last name is required");
        }
        if (contactDetails != null) {
            if (contactDetails.getEmail() == null && contactDetails.getPhone() == null) {
                throw new CustomerDomainException("At least one contact method (email or phone) is required");
            }
            if (contactDetails.getEmail() != null && !isValidEmail(contactDetails.getEmail())) {
                throw new CustomerDomainException("Invalid email format");
            }
            if (contactDetails.getPhone() != null && !isValidPhone(contactDetails.getPhone())) {
                throw new CustomerDomainException("Invalid phone number format");
            }
        }
        if (businessProfile != null) {
            if (businessProfile.getCompany() == null || businessProfile.getCompany().isBlank()) {
                throw new CustomerDomainException("Company name is required in business profile");
            }
            if (businessProfile.getBrandColors() != null) {
                validateBrandColors(businessProfile.getBrandColors());
            }
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^\\+?[0-9]{7,15}$");
    }

    private void validateContactDetails(ContactDetails details) {
        if (details == null) {
            throw new CustomerDomainException("Contact details cannot be null");
        }
        if (details.getEmail() == null && details.getPhone() == null) {
            throw new CustomerDomainException("At least one contact method (email or phone) is required");
        }
        if (details.getEmail() != null && !isValidEmail(details.getEmail())) {
            throw new CustomerDomainException("Invalid email format");
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public void updateBusinessProfile(BusinessProfile newProfile) {
        validateBusinessProfile(newProfile);
        this.businessProfile = newProfile;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateBusinessProfile(BusinessProfile profile) {
        if (profile == null) {
            throw new CustomerDomainException("Business profile cannot be null");
        }
        if (profile.getCompany() == null || profile.getCompany().isBlank()) {
            throw new CustomerDomainException("Company name is required in business profile");
        }
        if (profile.getBrandColors() != null) {
            validateBrandColors(profile.getBrandColors());
        }
    }

    private void validateBrandColors(String brandColors) {
        String[] colors = brandColors.split(",");
        for (String color : colors) {
            if (!color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
                throw new CustomerDomainException("Invalid brand color format: " + color);
            }
        }
    }

    public void updatePersonalInfo(PersonalInfo newPersonalInfo) {
        if (newPersonalInfo == null) {
            throw new IllegalArgumentException("Personal info cannot be null");
        }
        if (newPersonalInfo.getFirstName() == null || newPersonalInfo.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (newPersonalInfo.getLastName() == null || newPersonalInfo.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        this.personalInfo = newPersonalInfo;
        this.updatedAt = LocalDateTime.now();
    }


    public void addOpportunity(Long opportunityId) {
        if (opportunityId == null) {
            throw new CustomerDomainException("Opportunity ID cannot be null");
        }
        if (opportunities.contains(opportunityId)) {
            throw new CustomerDomainException("Opportunity already exists for this customer");
        }
        if (isBlocked()) {
            throw new CustomerDomainException("Cannot add opportunities to a blocked customer");
        }
        this.opportunities.add(opportunityId);
        this.updatedAt = LocalDateTime.now();
    }

    public void addInteraction(Long interactionId) {
        if (interactionId == null) {
            throw new CustomerDomainException("Interaction ID cannot be null");
        }
        if (interactions.contains(interactionId)) {
            throw new CustomerDomainException("Interaction already exists for this customer");
        }
        if (isBlocked()) {
            throw new CustomerDomainException("Cannot add interactions to a blocked customer");
        }
        this.interactions.add(interactionId);
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isHighValueCustomer() {
        return opportunities.size() > 5;
    }

    public boolean matchesTargetMarket(String targetMarket) {
        if (businessProfile == null || businessProfile.getTargetMarket() == null) {
            return false;
        }
        return businessProfile.getTargetMarket().equalsIgnoreCase(targetMarket);
    }
}