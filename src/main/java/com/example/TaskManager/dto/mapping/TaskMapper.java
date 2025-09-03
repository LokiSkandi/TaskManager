package com.example.TaskManager.dto.mapping;

import com.example.TaskManager.dto.TaskDto;
import com.example.TaskManager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskDto taskDto);
    TaskDto toDto(Task task);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TaskDto taskDto, @MappingTarget Task task);
}
