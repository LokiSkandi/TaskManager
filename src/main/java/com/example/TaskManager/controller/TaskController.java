package com.example.TaskManager.controller;

import com.example.TaskManager.castomException.NotFoundIdException;
import com.example.TaskManager.entity.Task;
import com.example.TaskManager.event.AuditEvent;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.service.KafkaAuditService;
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
    private TaskRepository rep;
    @Autowired
    private KafkaAuditService kafkaAuditService;

    @Operation(summary = "Получение всех задач")
    @GetMapping
    public Page<Task> getAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return rep.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Operation(summary = "Получение всех задач c completed==true")
    @GetMapping("/true")
    public Page<Task> getAllCompletedTrueTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return rep.findByCompletedTrue(PageRequest.of(page, size, Sort.by("name")));
    }

    @Operation(summary = "Добавление задач")
    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        Task taskOld = rep.save(task);
        AuditEvent auditEvent = new AuditEvent("task-service", "CREATE", "TASK", task.getId(), LocalDateTime.now());
        kafkaAuditService.sendAuditEvent(auditEvent);
        return rep.save(task);
    }

    @Operation(summary = "Получение задач по ID")
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена :с")
    })
    public Task getById (@PathVariable Long id) throws NotFoundIdException {
        return rep.findById(id).orElseThrow(() -> new NotFoundIdException("Нет данных по ID " + id));
    }

    @Operation(summary = "Удаление задачи по ID")
    @DeleteMapping("/{id}")
    public void deleteById (@Parameter(description = "ID задачи", example = "1") @PathVariable Long id)  throws NotFoundIdException {
        if(id == null) {
            throw new NotFoundIdException("Нет данных по ID " + id);
        }
        rep.deleteById(id);
    }

    @Operation(summary = "Изменение задачи по ID")
    @PutMapping("/{id}")
    public Task putById (@PathVariable Long id, @Valid @RequestBody Task newTask) throws NotFoundIdException{
        Task oldTask = rep.findById(id).orElseThrow(() -> new NotFoundIdException("Нет данных по ID " + id));
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setCompleted(newTask.isCompleted());
        Task upTask = createTask(oldTask);
        return upTask;
    }
}
