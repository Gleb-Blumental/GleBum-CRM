package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Order;
import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.TaskStatus;
import gleb.blum.examensarbete.models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByAssignedEmployeeId(String employeeId);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByOrder(Order order);
    List<Task> findByTicket(Ticket ticket);
    List<Task> findByDeadlineBefore(LocalDateTime deadline);
    List<Task> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);
}