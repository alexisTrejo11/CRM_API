package at.backend.CRM.CRM.Service;

import at.backend.CRM.CRM.Inputs.TaskInput;
import at.backend.CRM.CRM.Mappers.TaskMappers;
import at.backend.CRM.CRM.Models.Customer;
import at.backend.CRM.CRM.Models.Opportunity;
import at.backend.CRM.CRM.Models.Task;
import at.backend.CRM.CRM.Models.User;
import at.backend.CRM.CRM.Repository.CustomerRepository;
import at.backend.CRM.CRM.Repository.OpportunityRepository;
import at.backend.CRM.CRM.Repository.TaskRepository;
import at.backend.CRM.CRM.Repository.UserRepository;
import at.backend.CRM.CommonClasses.Exceptions.BusinessLogicException;
import at.backend.CRM.CommonClasses.Service.CommonService;
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
    public Page<Task> findAll(Pageable pageable) {
        return TaskRepository.findAll(pageable);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return TaskRepository.findById(id);
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
        Task existingTask =  TaskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

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
        boolean isTaskExisting = TaskRepository.existsById(id);
        if (!isTaskExisting) {
            throw new EntityNotFoundException("Task not found");
        }

        TaskRepository.deleteById(id);
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
}
