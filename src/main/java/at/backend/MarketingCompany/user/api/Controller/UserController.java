package at.backend.MarketingCompany.user.api.Controller;

import at.backend.MarketingCompany.common.utils.PageInput;
import at.backend.MarketingCompany.user.api.Service.UserServiceImpl;
import at.backend.MarketingCompany.user.api.Model.User;
import at.backend.MarketingCompany.user.infrastructure.UserInput;
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
public class UserController {

    private final UserServiceImpl service;

    @QueryMapping
    public Page<User> getAllUsers(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.getAll(pageable);
    }

    @QueryMapping
    public User getUserById(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public User createUser(@Argument UserInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UserInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
