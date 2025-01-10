package at.backend.CRM.Repository;

import at.backend.CRM.Models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByCompanyId(Long companyId);

    Optional<Contact> findByEmail(String email);

    @Query("SELECT c FROM Contact c WHERE c.company.id = :companyId " +
            "AND EXISTS (SELECT d FROM Deal d WHERE d.stage = 'CLOSED_WON' " +
            "AND :contactId MEMBER OF d.contacts)")
    List<Contact> findContactsWithClosedDeals(
            @Param("companyId") Long companyId,
            @Param("contactId") Long contactId
    );

    @Query("SELECT c FROM Contact c WHERE c.createdAt >= :since " +
            "AND SIZE(c.activities) = 0")
    List<Contact> findContactsWithNoActivitiesSince(
            @Param("since") LocalDateTime since
    );
}
