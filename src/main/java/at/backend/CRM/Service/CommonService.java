package at.backend.CRM.Service;

import at.backend.CRM.Utils.Result;

import java.util.List;
import java.util.Optional;

public interface CommonService<E, C> {
    List<E> findAll();
    Optional<E> findById(Long id);
    E create(C input);
    E update(Long id, C input);
    void delete(Long id);
    Result<Void> validate(C input);
}

