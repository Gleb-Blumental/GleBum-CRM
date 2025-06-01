package gleb.blum.examensarbete.controllers;

import gleb.blum.examensarbete.DTO.TaskDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.TaskStatus;
import gleb.blum.examensarbete.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = tasks.stream()
            .map(TaskDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id)
            .map(task -> ResponseEntity.ok(TaskDTO.fromEntity(task)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task newTask = taskService.createTask(taskDTO);
        return ResponseEntity
            .created(URI.create("/api/tasks/" + newTask.getId()))
            .body(TaskDTO.fromEntity(newTask));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDTO);
            return ResponseEntity.ok(TaskDTO.fromEntity(updatedTask));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        List<TaskDTO> taskDTOs = tasks.stream()
            .map(TaskDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }
    
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<TaskDTO>> getTasksByOrderId(@PathVariable String orderId) {
        try {
            List<Task> tasks = taskService.getTasksByOrderId(orderId);
            List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(taskDTOs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TaskDTO>> getTasksByTicketId(@PathVariable String ticketId) {
        try {
            List<Task> tasks = taskService.getTasksByTicketId(ticketId);
            List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(taskDTOs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable String id,
            @RequestParam TaskStatus status) {
        try {
            Task updatedTask = taskService.updateTaskStatus(id, status);
            return ResponseEntity.ok(TaskDTO.fromEntity(updatedTask));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}