package at.backend.CRM.Repository;

import at.backend.CRM.Models.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

}
