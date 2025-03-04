package at.backend.MarketingCompany.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<E, C, ID> {
    Page<E> getAll(Pageable pageable);
    E getById(ID id);
    E create(C input);
    E update(ID id, C input);
    void delete(ID id);
    void validate(C input);
}

