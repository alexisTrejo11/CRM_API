package at.backend.MarketingCompany.crm.tasks.api.service;

import at.backend.MarketingCompany.common.exceptions.BusinessLogicException;
import at.backend.MarketingCompany.common.service.CommonService;
import at.backend.MarketingCompany.crm.tasks.api.repository.TaskRepository;
import at.backend.MarketingCompany.crm.tasks.infrastructure.DTOs.TaskInput;
import at.backend.MarketingCompany.crm.tasks.infrastructure.autoMappers.TaskMappers;
import at.backend.MarketingCompany.crm.opportunity.domain.Opportunity;
import at.backend.MarketingCompany.crm.tasks.domain.Task;
import at.backend.MarketingCompany.customer.User;
import at.backend.MarketingCompany.crm.opportunity.api.repository.OpportunityRepository;
import at.backend.MarketingCompany.customer.UserRepository;
import at.backend.MarketingCompany.customer.api.repository.CustomerRepository;
import at.backend.MarketingCompany.customer.domain.Customer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements CommonService<Task, TaskInput> {

    public final TaskRepository TaskRepository;
    public final TaskMappers TaskMappers;
    public final CustomerRepository customerRepository;
    public final OpportunityRepository opportunityRepository;
    public final UserRepository userRepository;

    @Override
    public Page<Task> getAll(Pageable pageable) {
        return TaskRepository.findAll(pageable);
    }

    @Override
    public Task getById(Long id) {
        return getTask(id);
    }

    @Override
    public Task create(TaskInput input) {
        Task newTask = TaskMappers.inputToEntity(input);

        newTask.setCustomer(getCustomer(input.customerId()));
        newTask.setOpportunity(getOpportunity(input.opportunityId()));

        if (input.assignedToUserId() != null) {
            newTask.setAssignedTo(getUser(input.assignedToUserId()));
        }

        TaskRepository.saveAndFlush(newTask);

        return newTask;
    }

    @Override
    public Task update(Long id, TaskInput input) {
        Task existingTask = getTask(id);

        Task updatedTask = TaskMappers.inputToUpdatedEntity(existingTask, input);

        updatedTask.setCustomer(getCustomer(input.customerId()));
        updatedTask.setOpportunity(getOpportunity(input.opportunityId()));

        if (input.assignedToUserId() != null) {
            updatedTask.setAssignedTo(getUser(input.assignedToUserId()));
        }


        TaskRepository.saveAndFlush(updatedTask);

        return updatedTask;
    }

    @Override
    public void delete(Long id) {
        Task task = getTask(id);

        TaskRepository.delete(task);
    }

    @Override
    public void validate(TaskInput input) {
        Optional<Customer> customer = customerRepository.findById(input.customerId());
        if (customer.isEmpty()) {
            throw new EntityNotFoundException("Customer Not Found");
        }

        if (input.dueDate() != null) {
            if (input.dueDate().isBefore(LocalDateTime.now())) {
                throw new BusinessLogicException("Expected due date cannot be in the past");
            }

            LocalDateTime maxCloseDate = LocalDateTime.now().plusYears(1); // Max Date allowed (1 year)
            if (input.dueDate().isAfter(maxCloseDate)) {
                throw new BusinessLogicException("Expected due date cannot be more than one year in the future");
            }
        }
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Not found"));
    }

    private Opportunity getOpportunity(Long opportunityId) {
        return opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity Not found"));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
    }

    private Task getTask(Long id) {
        return TaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task Not found"));
    }
}
