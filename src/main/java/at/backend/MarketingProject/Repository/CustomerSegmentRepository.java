package at.backend.MarketingProject.Repository;

import at.backend.MarketingProject.Models.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Long> {
    List<CustomerSegment> findByDynamic(boolean dynamic);
}