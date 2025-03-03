package at.backend.MarketingCompany.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<E, C> {
    Page<E> getAll(Pageable pageable);
    E getById(Long id);
    E create(C input);
    E update(Long id, C input);
    void delete(Long id);
    void validate(C input);
}

