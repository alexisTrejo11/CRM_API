package at.backend.CRM.Repository;

import at.backend.CRM.Models.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {

}
