package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.Worker;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class WorkerDTO {
    private String id;
    private String name;
    private String email;
    private List<TaskDTO> tasks;

    public static WorkerDTO fromEntity(Worker worker) {
        WorkerDTO dto = new WorkerDTO();
        dto.setId(worker.getId());
        dto.setName(worker.getName());
        dto.setEmail(worker.getEmail());
        
        if (worker.getTasks() != null) {
            dto.setTasks(worker.getTasks().stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public Worker toEntity() {
        Worker worker = new Worker();
        worker.setName(this.name);
        worker.setEmail(this.email);
        return worker;
    }
}