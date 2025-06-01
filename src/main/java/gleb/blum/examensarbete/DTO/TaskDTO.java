package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.models.Task;
import gleb.blum.examensarbete.models.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime deadline;
    private String orderId;
    private String ticketId;

    public static TaskDTO fromEntity(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDeadline(task.getDeadline());
        
        if (task.getOrder() != null) {
            dto.setOrderId(task.getOrder().getId());
        }
        
        if (task.getTicket() != null) {
            dto.setTicketId(task.getTicket().getId());
        }
        
        return dto;
    }
    
    public Task toEntity() {
        Task task = new Task();
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setStatus(this.status);
        task.setDeadline(this.deadline);
        return task;
    }
}