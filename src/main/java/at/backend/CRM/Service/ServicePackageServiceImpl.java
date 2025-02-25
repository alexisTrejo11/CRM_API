package at.backend.CRM.Service;

import at.backend.CRM.Inputs.ServicePackageInput;
import at.backend.CRM.Mappers.ServicePackageMappers;
import at.backend.CRM.Models.ServicePackage;
import at.backend.CRM.Repository.ServicePackageRepository;
import at.backend.CRM.Utils.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicePackageServiceImpl implements  CommonService<ServicePackage, ServicePackageInput>{

    public final ServicePackageRepository servicePackageRepository;
    public final ServicePackageMappers servicePackageMappers;

    @Override
    public Page<ServicePackage> findAll(Pageable pageable) {
        return servicePackageRepository.findAll(pageable);
    }

    @Override
    public Optional<ServicePackage> findById(Long id) {
        return servicePackageRepository.findById(id);
    }

    @Override
    public ServicePackage create(ServicePackageInput input) {
        ServicePackage newServicePackage = servicePackageMappers.inputToEntity(input);

        servicePackageRepository.saveAndFlush(newServicePackage);

        return newServicePackage;
    }

    @Override
    public ServicePackage update(Long id, ServicePackageInput input) {
        ServicePackage existingServicePackage =  servicePackageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("service package not found"));

        ServicePackage updatedServicePackage = servicePackageMappers.inputToUpdatedEntity(existingServicePackage, input);

        servicePackageRepository.saveAndFlush(updatedServicePackage);

        return updatedServicePackage;
    }

    @Override
    public void delete(Long id) {
        boolean isServicePackageExisting = servicePackageRepository.existsById(id);
        if (!isServicePackageExisting) {
            throw new EntityNotFoundException("Service Package not found");
        }

        servicePackageRepository.deleteById(id);
    }

    @Override
    public void validate(ServicePackageInput input) {
        if (input.estimatedHours() != null && input.estimatedHours() > 1000) {
            throw new BusinessLogicException("Estimated hours must not exceed 1000.");
        }

        if (Boolean.TRUE.equals(input.isRecurring()) && input.frequency() == null) {
            throw new BusinessLogicException("Frequency must be provided for recurring services.");
        }

        if (Boolean.FALSE.equals(input.isRecurring()) && input.frequency() != null) {
            throw new BusinessLogicException("Frequency should not be set for non-recurring services.");
        }

        if (input.projectDuration() != null && input.projectDuration() > 36) {
            throw new BusinessLogicException("Project duration must not exceed 36 months.");
        }

        if (input.price().compareTo(new BigDecimal("1000000")) > 0) {
            throw new BusinessLogicException("Price must not exceed 1,000,000.");
        }

    }

}
