package at.backend.MarketingCompany.customer.domain.ValueObjects;

import java.util.UUID;

public record CustomerId(UUID value) {
    public static CustomerId of(UUID id) {
        return new CustomerId(id);
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }
}
