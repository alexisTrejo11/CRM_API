package at.backend.CRM.CommonClasses.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommonService<E, C> {
    Page<E> findAll(Pageable pageable);
    Optional<E> findById(Long id);
    E create(C input);
    E update(Long id, C input);
    void delete(Long id);
    void validate(C input);
}

