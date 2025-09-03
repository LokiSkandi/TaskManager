package com.example.TaskManager.controller;

import com.example.TaskManager.castomException.NotFoundIdException;
import com.example.TaskManager.dto.TaskDto;
import com.example.TaskManager.entity.Task;
import com.example.TaskManager.event.AuditEvent;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.service.KafkaAuditService;
import com.example.TaskManager.service.taskServise.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/api/tasks")
@Tag(name = "Task API", description = "Управление задачами")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private KafkaAuditService kafkaAuditService;

    @Operation(summary = "Получение всех задач")
    @GetMapping
    public Page<TaskDto> getAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return taskService.getAllTasks(page, size);
    }

    @Operation(summary = "Получение всех задач c completed==true")
    @GetMapping("/true")
    public Page<TaskDto> getAllCompletedTrueTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return taskService.getAllCompletedTrueTasks(page, size);
    }

    @Operation(summary = "Добавление задач")
    @PostMapping
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @Operation(summary = "Получение задач по ID")
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена :с")
    })
    public TaskDto getById (@PathVariable Long id) throws NotFoundIdException {
        return taskService.getById(id);
    }

    @Operation(summary = "Удаление задачи по ID")
    @DeleteMapping("/{id}")
    public void deleteById (@Parameter(description = "ID задачи", example = "1") @PathVariable Long id)  throws NotFoundIdException {
        taskService.deleteById(id);
    }

    @Operation(summary = "Изменение задачи по ID")
    @PutMapping("/{id}")
    public TaskDto putById (@PathVariable Long id, @Valid @RequestBody TaskDto newTaskDto) throws NotFoundIdException{
        return taskService.putById(id, newTaskDto);
    }
}
