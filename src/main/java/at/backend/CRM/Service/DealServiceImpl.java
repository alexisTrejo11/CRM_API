package at.backend.CRM.Service;

import at.backend.CRM.Inputs.DealInput;
import at.backend.CRM.Mappers.DealMappers;
import at.backend.CRM.Models.Deal;
import at.backend.CRM.Repository.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements CommonService<Deal, DealInput> {

    private final DealRepository companyRepository;
    private final DealMappers companyMapper;

    @Override
    public List<Deal> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Deal> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Deal create(DealInput input) {
        Deal company = companyMapper.createInputToEntity(input);

        companyRepository.saveAndFlush(company);

        return company;
    }

    @Override
    public Deal update(Long id, DealInput input) {
        Deal existingDeal = companyRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Deal not found"));

        Deal companyUpdated = companyMapper.updateInputToEntity(existingDeal, input);
        companyRepository.saveAndFlush(companyUpdated);

        return companyUpdated;
    }

    @Override
    public void delete(Long id) {
        boolean isDealExisting = companyRepository.existsById(id);
        if (!isDealExisting) {
            throw new EntityNotFoundException("Deal not found");
        }

        companyRepository.deleteById(id);
    }
}
