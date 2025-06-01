package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.models.Ticket;
import gleb.blum.examensarbete.status.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDTO {
    private String id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
    private TicketStatus status;
    private String priority;
    private String customerId;
    private String customerName;
    private String assignedWorkerId;
    private String assignedWorkerName;
    
    public static TicketDTO fromEntity(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setCreatedDate(ticket.getCreatedDate());
        dto.setLastUpdated(ticket.getLastUpdated());
        dto.setStatus(ticket.getStatus());
        dto.setPriority(ticket.getPriority());
        
        if (ticket.getCustomer() != null) {
            dto.setCustomerId(ticket.getCustomer().getId());
            dto.setCustomerName(ticket.getCustomer().getName());
        }
        
        if (ticket.getAssignedWorker() != null) {
            dto.setAssignedWorkerId(ticket.getAssignedWorker().getId());
            dto.setAssignedWorkerName(ticket.getAssignedWorker().getName());
        }
        
        return dto;
    }
    
    public Ticket toEntity() {
        Ticket ticket = new Ticket();
        ticket.setTitle(this.title);
        ticket.setDescription(this.description);
        ticket.setPriority(this.priority);
        
        if (this.status != null) {
            ticket.setStatus(this.status);
        }
        
        return ticket;
    }
}