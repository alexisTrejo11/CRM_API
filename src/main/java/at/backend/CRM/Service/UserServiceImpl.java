package at.backend.CRM.Service;

import at.backend.CRM.Inputs.UserInput;
import at.backend.CRM.Mappers.UserMappers;
import at.backend.CRM.Models.User;
import at.backend.CRM.Repository.UserRepository;
import at.backend.CRM.Utils.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CommonService<User, UserInput> {

    private final UserRepository companyRepository;
    private final UserMappers companyMapper;

    @Override
    public List<User> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public User create(UserInput input) {
        User company = companyMapper.createInputToEntity(input);

        companyRepository.saveAndFlush(company);

        return company;
    }

    @Override
    public User update(Long id, UserInput input) {
        User existingUser = companyRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("User not found"));

        User companyUpdated = companyMapper.updateInputToEntity(existingUser, input);
        companyRepository.saveAndFlush(companyUpdated);

        return companyUpdated;
    }

    @Override
    public void delete(Long id) {
        boolean isUserExisting = companyRepository.existsById(id);
        if (!isUserExisting) {
            throw new EntityNotFoundException("User not found");
        }

        companyRepository.deleteById(id);
    }

    @Override
    public void validate(UserInput input) {
    }
}
