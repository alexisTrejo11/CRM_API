package at.backend.CRM.CRM.Service;

import at.backend.CRM.CRM.Inputs.UserInput;
import at.backend.CRM.CRM.Mappers.UserMappers;
import at.backend.CRM.CRM.Models.User;
import at.backend.CRM.CRM.Repository.UserRepository;
import at.backend.CRM.CommonClasses.PasswordHandler;
import at.backend.CRM.CommonClasses.Service.CommonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CommonService<User, UserInput> {

    public final UserRepository userRepository;
    public final FieldValidationService fieldValidationService;
    public final UserMappers userMappers;

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long id) {
        return getUser(id);
    }

    @Override
    public User create(UserInput input) {
        User newUser = userMappers.inputToEntity(input);

        String hashedPassword = PasswordHandler.hashPassword(input.password());
        newUser.setPassword(hashedPassword);

        userRepository.saveAndFlush(newUser);

        return newUser;
    }

    @Override
    public User update(Long id, UserInput input) {
        User existingUser = getUser(id);

        User updatedUser = userMappers.inputToUpdatedEntity(existingUser, input);

        String hashedPassword = PasswordHandler.hashPassword(input.password());
        updatedUser.setPassword(hashedPassword);

        userRepository.saveAndFlush(updatedUser);

        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        User user = getUser(id);

        userRepository.delete(user);
    }

    @Override
    public void validate(UserInput input) {
        fieldValidationService.validateUsername(input.username());
        fieldValidationService.validateEmail(input.email());
        fieldValidationService.validatePassword(input.password());
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }
}
