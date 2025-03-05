package at.backend.MarketingCompany.customer.domain.ValueObjects;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContactDetails {
    private final String email;
    private final String phone;
}
