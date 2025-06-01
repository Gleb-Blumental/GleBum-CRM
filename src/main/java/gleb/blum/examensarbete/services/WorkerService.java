package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.TaskStatus;
import gleb.blum.examensarbete.models.Worker;
import gleb.blum.examensarbete.repositories.TaskRepository;
import gleb.blum.examensarbete.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, TaskRepository taskRepository) {
        this.workerRepository = workerRepository;
        this.taskRepository = taskRepository;
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorkerById(String id) {
        return workerRepository.findById(id);
    }

    public Worker createWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public Worker updateWorker(String id, Worker workerDetails) {
        Worker existingWorker = workerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + id));

        existingWorker.setName(workerDetails.getName());
        existingWorker.setEmail(workerDetails.getEmail());

        return workerRepository.save(existingWorker);
    }

    public void deleteWorker(String id) {
        Worker worker = workerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + id));
        workerRepository.delete(worker);
    }

    public List<Task> getWorkerTasks(String workerId) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));
        return worker.getTasks();
    }

    public Task assignTaskToWorker(String workerId, String taskId) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (worker.getTasks() == null) {
            worker.setTasks(new ArrayList<>());
        }

        worker.getTasks().add(task);
        workerRepository.save(worker);

        return task;
    }

    public Task updateTaskStatus(String workerId, String taskId, TaskStatus status) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));

        Task task = null;
        if (worker.getTasks() != null) {
            task = worker.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElse(null);
        }

        if (task == null) {
            throw new ResourceNotFoundException("Task not found for worker with id: " + taskId);
        }

        task.setStatus(status);
        taskRepository.save(task);

        return task;
    }

    public void removeTaskFromWorker(String workerId, String taskId) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));

        if (worker.getTasks() != null) {
            worker.getTasks().removeIf(task -> task.getId().equals(taskId));
            workerRepository.save(worker);
        }
    }
}
