package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByAssignedEmployeeId(String employeeId);
}