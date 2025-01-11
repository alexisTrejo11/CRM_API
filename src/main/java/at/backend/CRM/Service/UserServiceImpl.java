package at.backend.CRM.Service;

import at.backend.CRM.Inputs.UserInput;
import at.backend.CRM.Mappers.UserMappers;
import at.backend.CRM.Models.User;
import at.backend.CRM.Repository.UserRepository;
import at.backend.CRM.Utils.PasswordHandler;
import at.backend.CRM.Utils.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CommonService<User, UserInput> {

    private final UserRepository userRepository;
    private final UserMappers companyMapper;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(UserInput input) {
        User user = companyMapper.createInputToEntity(input);

        String hashedPassword = PasswordHandler.hashPassword(input.password());
        user.setPassword(hashedPassword);

        userRepository.saveAndFlush(user);

        return user;
    }

    @Override
    public User update(Long id, UserInput input) {
        User existingUser = userRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("User not found"));

        User companyUpdated = companyMapper.updateInputToEntity(existingUser, input);
        userRepository.saveAndFlush(companyUpdated);

        return companyUpdated;
    }

    @Override
    public void delete(Long id) {
        boolean isUserExisting = userRepository.existsById(id);
        if (!isUserExisting) {
            throw new EntityNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

    @Override
    public void validate(UserInput input) {
        Optional<User> user = userRepository.findByEmail(input.email());
        if (user.isPresent()) {
            throw new EntityNotFoundException("Email already taken");
        }
    }
}
