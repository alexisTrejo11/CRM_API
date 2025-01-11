package at.backend.CRM.Repository;

import at.backend.CRM.Models.Customer;
import at.backend.CRM.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}