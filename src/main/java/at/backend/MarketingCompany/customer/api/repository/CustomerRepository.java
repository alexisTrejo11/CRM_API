package at.backend.MarketingCompany.customer.api.repository;

import at.backend.MarketingCompany.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}