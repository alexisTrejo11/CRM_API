package at.backend.MarketingCompany.crm.tasks.infrastructure.autoMappers;

import at.backend.MarketingCompany.crm.tasks.domain.Task;
import at.backend.MarketingCompany.crm.tasks.infrastructure.DTOs.TaskInput;
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
