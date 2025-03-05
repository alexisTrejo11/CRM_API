package at.backend.MarketingCompany.customer.domain.ValueObjects;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PersonalInfo {
    private final String firstName;
    private final String lastName;
}
