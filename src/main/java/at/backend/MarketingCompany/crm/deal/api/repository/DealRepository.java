package at.backend.MarketingCompany.crm.deal.api.repository;

import at.backend.MarketingCompany.crm.deal.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealRepository extends JpaRepository<Deal, UUID> {

}