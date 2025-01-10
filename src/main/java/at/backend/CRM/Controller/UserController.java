package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.UserInput;
import at.backend.CRM.Models.User;
import at.backend.CRM.Service.CommonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final CommonService<User, UserInput> service;

    public UserController(CommonService<User, UserInput> service) {
        this.service = service;
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @QueryMapping
    public User getUserById(@Argument Long id) {
        return service.findById(id)
                .orElse(new User());
    }

    @MutationMapping
    public User createUser(@Argument UserInput input) {
        return service.create(input);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UserInput input) {
        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
