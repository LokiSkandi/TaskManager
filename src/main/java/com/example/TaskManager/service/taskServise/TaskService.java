package com.example.TaskManager.service.taskServise;

import com.example.TaskManager.dto.TaskDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskDto> getAllTasks(int page, int size, Boolean coompleted);
    TaskDto createTask(TaskDto taskDto);
    TaskDto getById(Long id);
    void deleteById(Long id);
    TaskDto putById(Long id, TaskDto taskDto);

}
