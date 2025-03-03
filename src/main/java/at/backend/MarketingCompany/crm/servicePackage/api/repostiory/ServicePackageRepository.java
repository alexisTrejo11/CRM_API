package at.backend.MarketingCompany.crm.servicePackage.api.repostiory;

import at.backend.MarketingCompany.crm.servicePackage.domain.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {

}
