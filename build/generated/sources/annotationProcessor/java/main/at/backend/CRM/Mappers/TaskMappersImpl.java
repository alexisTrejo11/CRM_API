package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.TaskInput;
import at.backend.CRM.Models.Task;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T14:49:06-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class TaskMappersImpl implements TaskMappers {

    @Override
    public Task inputToEntity(TaskInput input) {
        if ( input == null ) {
            return null;
        }

        Task task = new Task();

        task.setTitle( input.title() );
        task.setDescription( input.description() );
        task.setDueDate( input.dueDate() );
        task.setStatus( input.status() );
        task.setPriority( input.priority() );

        return task;
    }

    @Override
    public Task inputToUpdatedEntity(Task existingTask, TaskInput input) {
        if ( input == null ) {
            return existingTask;
        }

        existingTask.setTitle( input.title() );
        existingTask.setDescription( input.description() );
        existingTask.setDueDate( input.dueDate() );
        existingTask.setStatus( input.status() );
        existingTask.setPriority( input.priority() );

        return existingTask;
    }
}
