package at.backend.CRM.Service;

import at.backend.CRM.Inputs.CompanyInput;
import at.backend.CRM.Mappers.CompanyMappers;
import at.backend.CRM.Models.Company;
import at.backend.CRM.Repository.CompanyRepository;
import at.backend.CRM.Utils.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CommonService<Company, CompanyInput> {

    private final CompanyRepository companyRepository;
    private final CompanyMappers companyMappers;

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company create(CompanyInput input) {
        Company company = companyMappers.createInputToEntity(input);
        companyRepository.saveAndFlush(company);

        return company;
    }


    @Override
    public void validate(CompanyInput input) {
        Optional<Company> company = companyRepository.findByName(input.name());
        if (company.isPresent()) {
           throw new RuntimeException("Company already created");
        }
    }

    @Override
    public Company update(Long id, CompanyInput input) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Company not found"));

        Company companyUpdated = companyMappers.updateInputToEntity(existingCompany, input);
        companyRepository.saveAndFlush(companyUpdated);

        return companyUpdated;
    }

    @Override
    public void delete(Long id) {
        boolean isCompanyExisting = companyRepository.existsById(id);
        if (!isCompanyExisting) {
            throw new EntityNotFoundException("Company not found");
        }

        companyRepository.deleteById(id);
    }
}
