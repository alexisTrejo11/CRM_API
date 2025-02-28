package at.backend.CRM.CRM.Repository;

import at.backend.CRM.CRM.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}