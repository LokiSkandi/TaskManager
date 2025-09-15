package com.example.TaskManager.service.taskServise;

import com.example.TaskManager.castomException.NotFoundIdException;
import com.example.TaskManager.dto.TaskDto;
import com.example.TaskManager.dto.mapping.TaskMapper;
import com.example.TaskManager.entity.Task;
import com.example.TaskManager.event.AuditEvent;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.service.KafkaAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private KafkaAuditService kafkaAuditService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, KafkaAuditService kafkaAuditService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.kafkaAuditService = kafkaAuditService;
    }

    @Override
    public Page<TaskDto> getAllTasks(int page, int size, Boolean completed) {
            return taskRepository.findByCompleted(completed,PageRequest.of(page, size))
                    .map(taskMapper::toDto);
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);

        AuditEvent auditEvent = new AuditEvent("task-service", "CREATE", "TASK", savedTask.getId(), LocalDateTime.now());
        kafkaAuditService.sendAuditEvent(auditEvent);

        return taskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException("Нет данных по ID " + id));
        return taskMapper.toDto(task);
    }

    @Override
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundIdException("Нет данных по ID " + id);
        }
        taskRepository.deleteById(id);

        AuditEvent auditEvent = new AuditEvent("task-service", "DELETE", "TASK", id, LocalDateTime.now());
        kafkaAuditService.sendAuditEvent(auditEvent);
    }

    @Override
    public TaskDto putById(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException("Нет данных по ID " + id));

        taskMapper.updateEntityFromDto(taskDto, existingTask);
        Task updatedTask = taskRepository.save(existingTask);

        return taskMapper.toDto(updatedTask);
    }
}
