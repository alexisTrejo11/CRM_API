package at.backend.CRM.CRM.Service;

import at.backend.CRM.CRM.Repository.UserRepository;
import at.backend.CRM.CommonClasses.Exceptions.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldValidationService {

    private final UserRepository userRepository;

    public void validateEmail(String email) {
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new BusinessLogicException("Email must be a valid email address.");
        }
        if (emailAlreadyExists(email)) {
            throw new BusinessLogicException("Email is already in use.");
        }
    }

    public void validateUsername(String username) {
        if (username.trim().isEmpty()) {
            throw new BusinessLogicException("Username cannot be blank.");
        }
        if (username.length() > 50) {
            throw new BusinessLogicException("Username cannot exceed 50 characters.");
        }
        if (!username.matches("^[A-Za-z0-9_]{3,50}$")) {
            throw new BusinessLogicException("Username must contain only letters, digits, and underscores.");
        }
        if (usernameAlreadyExists(username)) {
            throw new BusinessLogicException("Username is already in use.");
        }
    }

    public void validatePassword(String password) {
        if (password.trim().isEmpty()) {
            throw new BusinessLogicException("Password cannot be blank.");
        }
        if (password.length() < 8) {
            throw new BusinessLogicException("Password must be at least 8 characters long.");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new BusinessLogicException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character(@$!%*?&)."
            );
        }
    }

    public void validatePhone(String phone) {
        if (phone.trim().isEmpty()) {
            throw new BusinessLogicException("Phone cannot be blank.");
        }
        if (phone.length() > 20) {
            throw new BusinessLogicException("Phone cannot exceed 20 characters.");
        }
        if (!phone.matches("\\+?\\d{7,15}")) {
            throw new BusinessLogicException("Phone number is invalid. Must contain only digits and optional '+'.");
        }
    }


    public boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameAlreadyExists(String email) {
        return userRepository.existsByUsername(email);
    }
}

