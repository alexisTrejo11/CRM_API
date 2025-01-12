package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.TaskInput;
import at.backend.CRM.Models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMappers {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task inputToEntity(TaskInput input);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task inputToUpdatedEntity(@MappingTarget Task existingTask, TaskInput input);
         
}
