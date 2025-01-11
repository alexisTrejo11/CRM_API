package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.CompanyInput;
import at.backend.CRM.Models.Company;
import at.backend.CRM.Service.CommonService;
import at.backend.CRM.Utils.Result;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CompanyController {

    private final CommonService<Company, CompanyInput> service;

    public CompanyController(CommonService<Company, CompanyInput> service) {
        this.service = service;
    }

    @QueryMapping
    public List<Company> getAllCompanies() {
        return service.findAll();
    }

    @QueryMapping
    public Company getCompanyById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Company());
    }

    @MutationMapping
    public Company createCompany(@Valid @Argument CompanyInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Company updateCompany(@Argument Long id, @Argument CompanyInput input) {
        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteCompany(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
