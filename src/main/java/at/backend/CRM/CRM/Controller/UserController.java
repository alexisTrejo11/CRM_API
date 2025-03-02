package at.backend.CRM.CRM.Controller;

import at.backend.CRM.CRM.Inputs.PageInput;
import at.backend.CRM.CRM.Inputs.UserInput;
import at.backend.CRM.CRM.Models.User;
import at.backend.CRM.CommonClasses.Service.CommonService;
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

    private final CommonService<User, UserInput> service;

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
