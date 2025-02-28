package at.backend.CRM.CRM.Repository;

import at.backend.CRM.CRM.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}