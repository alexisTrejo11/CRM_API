package at.backend.MarketingCompany.customer.domain.excpetions;

public class CustomerDomainException extends RuntimeException {
    public CustomerDomainException(String message) {
        super(message);
    }
}
