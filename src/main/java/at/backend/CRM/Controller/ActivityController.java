package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.ActivityInput;
import at.backend.CRM.Models.Activity;
import at.backend.CRM.Service.CommonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ActivityController {

    private final CommonService<Activity, ActivityInput> service;

    public ActivityController(CommonService<Activity, ActivityInput> service) {
        this.service = service;
    }

    @QueryMapping
    public List<Activity> getAllActivities() {
        return service.findAll();
    }

    @QueryMapping
    public Activity getActivityById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Activity());
    }

    @MutationMapping
    public Activity createActivity(@Argument ActivityInput input) {
        return service.create(input);
    }

    @MutationMapping
    public Activity updateActivity(@Argument Long id, @Argument ActivityInput input) {
        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteActivity(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
