package at.backend.CRM.Repository;

import at.backend.CRM.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}