package at.backend.MarketingCompany.crm.tasks.api.repository;

import at.backend.MarketingCompany.crm.tasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}