package at.backend.MarketingCompany.crm.tasks.api.controller;

import at.backend.MarketingCompany.crm.tasks.api.service.TaskServiceImpl;
import at.backend.MarketingCompany.crm.tasks.infrastructure.DTOs.TaskInput;
import at.backend.MarketingCompany.common.utils.PageInput;
import at.backend.MarketingCompany.crm.tasks.domain.Task;
import at.backend.MarketingCompany.common.service.CommonService;
import jakarta.validation.Valid;
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
public class TaskController {
    private final TaskServiceImpl service;

    @QueryMapping
    public Page<Task> getAllTasks(@Argument PageInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.size());

        return service.getAll(pageable);
    }

    @QueryMapping
    public Task getTaskById(@Argument Long id) {
        return service.getById(id);
    }

    @MutationMapping
    public Task createTask(@Valid @Argument TaskInput input) {
        service.validate(input);

        return service.create(input);
    }

    @MutationMapping
    public Task updateTask(@Valid @Argument Long id, @Argument TaskInput input) {
        service.validate(input);

        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteTask(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
