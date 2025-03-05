package at.backend.MarketingCompany.customer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

}