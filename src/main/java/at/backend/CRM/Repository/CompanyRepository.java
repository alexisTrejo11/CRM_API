package at.backend.CRM.Repository;

import at.backend.CRM.Models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByTaxNumber(String taxNumber);

    List<Company> findByIndustry(String industry);

    @Query("SELECT c FROM Company c WHERE SIZE(c.deals) > :minDeals")
    List<Company> findCompaniesWithMoreThanXDeals(@Param("minDeals") int minDeals);

    @Query("SELECT c FROM Company c LEFT JOIN c.deals d GROUP BY c " +
            "HAVING SUM(CASE WHEN d.stage = 'CLOSED_WON' THEN 1 ELSE 0 END) > 0")
    List<Company> findCompaniesWithClosedDeals();
}