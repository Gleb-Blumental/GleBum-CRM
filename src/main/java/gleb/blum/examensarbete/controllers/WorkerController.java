package gleb.blum.examensarbete.controllers;

import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.TaskStatus;
import gleb.blum.examensarbete.models.Worker;
import gleb.blum.examensarbete.services.WorkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<List<Worker>> getAllWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable String id) {
        return workerService.getWorkerById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Worker> createWorker(@Valid @RequestBody Worker worker) {
        Worker newWorker = workerService.createWorker(worker);
        return ResponseEntity
            .created(URI.create("/api/workers/" + newWorker.getId()))
            .body(newWorker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable String id, @Valid @RequestBody Worker worker) {
        try {
            Worker updatedWorker = workerService.updateWorker(id, worker);
            return ResponseEntity.ok(updatedWorker);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable String id) {
        try {
            workerService.deleteWorker(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getWorkerTasks(@PathVariable String id) {
        try {
            List<Task> tasks = workerService.getWorkerTasks(id);
            return ResponseEntity.ok(tasks);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{workerId}/tasks/{taskId}")
    public ResponseEntity<Task> assignTaskToWorker(
            @PathVariable String workerId,
            @PathVariable String taskId) {
        try {
            Task task = workerService.assignTaskToWorker(workerId, taskId);
            return ResponseEntity.ok(task);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{workerId}/tasks/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable String workerId,
            @PathVariable String taskId,
            @RequestParam TaskStatus status) {
        try {
            Task task = workerService.updateTaskStatus(workerId, taskId, status);
            return ResponseEntity.ok(task);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{workerId}/tasks/{taskId}")
    public ResponseEntity<Void> removeTaskFromWorker(
            @PathVariable String workerId,
            @PathVariable String taskId) {
        try {
            workerService.removeTaskFromWorker(workerId, taskId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
