package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.DealInput;
import at.backend.CRM.Models.Deal;
import at.backend.CRM.Service.CommonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DealController {

    private final CommonService<Deal, DealInput> service;

    public DealController(CommonService<Deal, DealInput> service) {
        this.service = service;
    }

    @QueryMapping
    public List<Deal> getAllDeals() {
        return service.findAll();
    }

    @QueryMapping
    public Deal getDealById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Deal());
    }

    @MutationMapping
    public Deal createDeal(@Argument DealInput input) {
        return service.create(input);
    }

    @MutationMapping
    public Deal updateDeal(@Argument Long id, @Argument DealInput input) {
        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteDeal(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
