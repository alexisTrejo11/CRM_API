package at.backend.CRM.Service;

import at.backend.CRM.Inputs.ServicePackageInput;
import at.backend.CRM.Mappers.ServicePackageMappers;
import at.backend.CRM.Models.ServicePackage;
import at.backend.CRM.Repository.ServicePackageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicePackageServiceImpl implements  CommonService<ServicePackage, ServicePackageInput>{

    public final ServicePackageRepository repository;
    public final ServicePackageMappers mappers;
    public final FieldValidationService fieldValidationService;

    @Override
    public Page<ServicePackage> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<ServicePackage> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ServicePackage create(ServicePackageInput input) {
        ServicePackage newServicePackage = mappers.inputToEntity(input);

        repository.saveAndFlush(newServicePackage);

        return newServicePackage;
    }

    @Override
    public ServicePackage update(Long id, ServicePackageInput input) {
        ServicePackage existingServicePackage =  repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("product not found"));

        ServicePackage updatedServicePackage = mappers.inputToUpdatedEntity(existingServicePackage, input);

        repository.saveAndFlush(updatedServicePackage);

        return updatedServicePackage;
    }

    @Override
    public void delete(Long id) {
        boolean isServicePackageExisting = repository.existsById(id);
        if (!isServicePackageExisting) {
            throw new EntityNotFoundException("product not found");
        }

        repository.deleteById(id);
    }

    @Override
    public void validate(ServicePackageInput input) {
    }
}
