package com.example.TaskManager.service.taskServise;

import com.example.TaskManager.dto.TaskDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskDto> getAllTasks(int page, int size);
    Page<TaskDto> getAllCompletedTrueTasks(int page, int size);
    TaskDto createTask(TaskDto taskDto);
    TaskDto getById(Long id);
    void deleteById(Long id);
    TaskDto putById(Long id, TaskDto taskDto);

}
