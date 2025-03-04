package at.backend.MarketingCompany.crm.servicePackage.api.controller;

import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.common.utils.PageInput;
import at.backend.MarketingCompany.crm.servicePackage.infrastructure.DTOs.ServicePackageInput;
import at.backend.MarketingCompany.crm.servicePackage.domain.ServicePackage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ServicePackageController {
    private final CommonService<ServicePackage, ServicePackageInput, Long> service;

    @QueryMapping
    public Page<ServicePackage> getAllServicePackages(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.getAll(pageable);
    }

    @QueryMapping
    public ServicePackage getServicePackageById(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public ServicePackage createServicePackage(@Valid @Argument ServicePackageInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public ServicePackage updateServicePackage(@Valid @Argument Long id, @Argument ServicePackageInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteServicePackage(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
