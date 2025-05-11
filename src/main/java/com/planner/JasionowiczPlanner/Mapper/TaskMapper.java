package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Task.TaskDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);
    Task toTask(TaskDTO taskDTO);
}
