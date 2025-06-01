package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.DTO.TaskDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Order;
import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.TaskStatus;
import gleb.blum.examensarbete.models.Ticket;
import gleb.blum.examensarbete.repositories.OrderRepository;
import gleb.blum.examensarbete.repositories.TaskRepository;
import gleb.blum.examensarbete.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    
    @Autowired
    public TaskService(
            TaskRepository taskRepository, 
            OrderRepository orderRepository, 
            TicketRepository ticketRepository) {
        this.taskRepository = taskRepository;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }
    
    public Task createTask(TaskDTO taskDTO) {
        Task task = taskDTO.toEntity();
        
        // Set associated Order if provided
        if (taskDTO.getOrderId() != null) {
            Order order = orderRepository.findById(taskDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + taskDTO.getOrderId()));
            task.setOrder(order);
        }
        
        // Set associated Ticket if provided
        if (taskDTO.getTicketId() != null) {
            Ticket ticket = ticketRepository.findById(taskDTO.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + taskDTO.getTicketId()));
            task.setTicket(ticket);
        }
        
        return taskRepository.save(task);
    }
    
    public Task updateTask(String id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        
        // Update basic task details
        if (taskDTO.getTitle() != null) {
            existingTask.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            existingTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getStatus() != null) {
            existingTask.setStatus(taskDTO.getStatus());
        }
        if (taskDTO.getDeadline() != null) {
            existingTask.setDeadline(taskDTO.getDeadline());
        }
        
        // Update Order association if provided
        if (taskDTO.getOrderId() != null) {
            Order order = orderRepository.findById(taskDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + taskDTO.getOrderId()));
            existingTask.setOrder(order);
        }
        
        // Update Ticket association if provided
        if (taskDTO.getTicketId() != null) {
            Ticket ticket = ticketRepository.findById(taskDTO.getTicketId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + taskDTO.getTicketId()));
            existingTask.setTicket(ticket);
        }
        
        return taskRepository.save(existingTask);
    }
    
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }
    
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    
    public List<Task> getTasksByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return taskRepository.findByOrder(order);
    }
    
    public List<Task> getTasksByTicketId(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
        return taskRepository.findByTicket(ticket);
    }
    
    public Task updateTaskStatus(String id, TaskStatus status) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}