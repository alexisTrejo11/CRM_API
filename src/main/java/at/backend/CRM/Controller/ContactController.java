package at.backend.CRM.Controller;

import at.backend.CRM.Inputs.ContactInput;
import at.backend.CRM.Models.Contact;
import at.backend.CRM.Service.CommonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ContactController {

    private final CommonService<Contact, ContactInput> service;

    public ContactController(CommonService<Contact, ContactInput> service) {
        this.service = service;
    }

    @QueryMapping
    public List<Contact> getAllContacts() {
        return service.findAll();
    }

    @QueryMapping
    public Contact getContactById(@Argument Long id) {
        return service.findById(id)
                .orElse(new Contact());
    }

    @MutationMapping
    public Contact createContact(@Argument ContactInput input) {
        return service.create(input);
    }

    @MutationMapping
    public Contact updateContact(@Argument Long id, @Argument ContactInput input) {
        return service.update(id, input);
    }

    @MutationMapping
    public boolean deleteContact(@Argument Long id) {
        service.delete(id);
        return true;
    }
}
